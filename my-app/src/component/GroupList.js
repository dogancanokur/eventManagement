import React, {Component} from 'react';
import {Button, ButtonGroup, Table} from "reactstrap";
import {Link} from "react-router-dom";

class GroupList extends Component {
    constructor(props) {
        super(props);
        this.state = {groups: [], isLoading: true};
        this.remove = this.remove.bind(this); // todo ?
    }

    componentDidMount() {
        this.setState({isLoading: false});
        fetch("api/groups")
            .then(response => response.json())
            .then(data => this.setState({groups: data, isLoading: false}));
    }

    remove(id) {
        fetch(`/api/groups/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedGroups = [...this.state.groups].filter(i => i.id !== id);
            this.setState({updatedGroups: updatedGroups});
        })
    }

    render() {

        const {groups, isLoading} = this.state;
        if (isLoading) {
            return (<p>Loading...</p>);
        }
        return (
            <div>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/groups/new">Add Group</Button>
                </div>
                <h3>Event List</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Location</th>
                        <th>Events</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        groups.map(group => {
                            return (
                                <tr key={group.id}>
                                    <td>{group.name}</td>
                                    <td>{group.address || ""} {group.city || ""} {group.stateorProvince || ""}</td>
                                    <td>{group.events.map(event => {
                                        return (<div key={event.id}>
                                            {new Intl.DateTimeFormat('tr-TR', {
                                                year: 'numeric',
                                                month: 'long',
                                                day: '2-digit'
                                            }).format(new Date(event.date))}: {event.title}
                                        </div>)
                                    })}</td>
                                    <td>
                                        <ButtonGroup>
                                            <Button size="sm"
                                                    tag={Link}
                                                    to={"/groups/" + group.id}
                                                    color="primary">Edit</Button>
                                            <Button size="sm"
                                                    tag={Link}
                                                    onClick={() => this.remove(group.id)}
                                                    color="danger">Delete</Button>
                                        </ButtonGroup>
                                    </td>
                                </tr>
                            )
                        })
                    }
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default GroupList;