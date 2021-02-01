import React, {Component} from 'react';
import AppNavbar from "./AppNavbar";
import {Button, Container} from "reactstrap";
import {Link} from "react-router-dom";

class Home extends Component {
    render() {
        return (
            <div>
                    <Button color="warning" tag={Link} to={"/groups"}>Events</Button>
            </div>
        );
    }
}

export default Home;