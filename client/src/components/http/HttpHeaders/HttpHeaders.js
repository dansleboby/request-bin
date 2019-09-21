import React from 'react';

const toHeaderRow = (header, value) => <tr key={header}><td><strong>{header}</strong></td><td>{value}</td></tr>;

const HttpHeaders = (props) => {
    const httpHeaders = props.httpHeaders;

    let httpHeadersElement = null;

    if (httpHeaders) {
        httpHeadersElement = httpHeaders.map(header => toHeaderRow(header.key, header.value));
    }

    return (
        <div className="card">
            <header className="card-header has-background-light">
                <p className="card-header-title">
                    HTTP Headers
                </p>
            </header>
            <div className="card-content">
                <div className="content">
                    <table className="table is-bordered">
                        <tbody>
                            {httpHeadersElement}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default HttpHeaders;