import React from 'react';

const HttpBody = (props) => (
    <div className="card">
        <header className="card-header has-background-light">
            <p className="card-header-title">
                Body
            </p>
        </header>
        <div className="card-content">
            <div className="content">
                <pre>
                    <code>{props.httpBody}</code>
                </pre>
            </div>
        </div>
    </div>
);

export default HttpBody;