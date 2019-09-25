import React, {useState} from 'react';
import './App.scss';
import LoadingLandingPage from "./containers/LandingPage/LoadingLandingPage";
import {BrowserRouter, Redirect, Route, Switch} from "react-router-dom";
import requestBinClient from "./http/RequestBinClient";
import RequestBinLandingPage from "./containers/LandingPage/RequestBinLandingPage";
import NotFoundLandingPage from "./containers/LandingPage/NotFoundLandingPage";

const App = () => {
    return (
        <BrowserRouter>
            <Switch>
                <Route path='/404' component={NotFoundLandingPage}/>
                <Route path='/:binId' component={RequestBinLandingPage}/>
                <Route path='/' component={LoadingLandingPage}/>
            </Switch>
        </BrowserRouter>
    );
};

export default App;
