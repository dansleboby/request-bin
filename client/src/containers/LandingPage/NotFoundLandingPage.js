import React from 'react';

const NotFoundLandingPage = () => {
    return (
        <section className="hero is-fullheight is-warning is-bold">
            <div className="hero-body">
                <div className="container">
                    <h1 className="title is-1 has-text-centered has-text-weight-light">
                        Wait! This Request Bin has expired.
                    </h1>
                    <h2 className="subtitle is-4 has-text-centered has-text-weight-light">
                        Or it never existed. Spooky. 🤔
                    </h2>
                </div>
            </div>
        </section>
    );
};

export default NotFoundLandingPage;