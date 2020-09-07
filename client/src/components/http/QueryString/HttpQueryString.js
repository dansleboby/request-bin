import React from 'react';

const toQueryParameterRow = (header, value) => <tr key={header}><td><strong>{header}</strong></td><td>{value}</td></tr>;

const HttpQueryString = (props) => {
    const queryParameters = props.queryParameters;

    if (queryParameters && Object.keys(queryParameters).length > 0) {
        const queryParametersElement = queryParameters.map(header => toQueryParameterRow(header.key, header.value));

        return (
            <div className="card">
                <header className="card-header has-background-light">
                    <p className="card-header-title">
                        Query Parameters
                    </p>
                </header>
                <div className="card-content">
                    <div className="content">
                        <table className="table is-bordered">
                            <tbody>
                            {queryParametersElement}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    } else {
        return null;
    }
};

export default HttpQueryString;