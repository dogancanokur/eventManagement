import React, {Component} from 'react';
import './App.css';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Home from "./component/Home";
import GroupList from "./component/GroupList";
import AppNavbar from "./component/AppNavbar";
import {Container} from "reactstrap";

class App extends Component {

    state = {
        isLoading: true,
        groups: []
    }

    async componentDidMount() {
        const response = await fetch('/api/groups');
        const body = await response.json();
        this.setState({groups: body, isLoading: false});
    }

    render() {
        const {groups, isLoading} = this.state;

        if (isLoading) {
            return (<p>Loading...</p>);
        }

        return (
            <BrowserRouter>
                <AppNavbar/>
                <Container fluid>
                    <Switch>
                        <Route path="/" exact component={Home}/>
                        <Route path="/groups" exact component={GroupList}/>
                    </Switch>
                </Container>
            </BrowserRouter>
        );
    }
}

export default App;