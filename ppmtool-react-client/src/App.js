import React from 'react';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';
import {BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from './components/Project/AddProject';

import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";


function App() {
  return (
    <Router>
      <div className="App">
        <Header />

        <Route exact path="/dashboard" component={Dashboard} />
        <Route exact path="/addProject" component={AddProject} />
      
      </div>
    </Router>
  );
}

export default App;
