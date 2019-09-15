import React, {useEffect} from 'react';
import logo from './logo.svg';
import './App.scss';

function App() {

    useEffect(() => {
        const eventSource = new EventSource('http://localhost:8080/test/stream');
        eventSource.onmessage = e => console.log(e);
    }, []);

    return (
        <section className="section">
            <div className="container">
                <h1 className="title">
                    Bucket
                </h1>
                <hr/>
            </div>
        </section>
    );
}

export default App;
