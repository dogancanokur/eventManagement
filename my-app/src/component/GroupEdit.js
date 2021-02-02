import React, {Component} from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link} from "react-router-dom";

class GroupEdit extends Component {
    emptyItem = {
        name: '',
        address: '',
        city: '',
        stateOrProvince: '',
        country: '',
        postalCode: ''
    }

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const group = await (await fetch(`/api/group/${this.props.match.params.id}`)).json();
            this.setState({item: group});
        }
    }

    handleChange = (event) => {
        const {name, value} = event.target;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch(`/api/group${item.id ? '/' + item.id : ''}`, {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });
        this.props.history.push('/groups');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Group' : 'Add Group'}</h2>
        return (
            <div>
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="name">Name</Label>
                            <Input type="text"
                                   id="name"
                                   name="name"
                                   value={item.name || ''}
                                   onChange={this.handleChange}
                                   placeholder="Name"
                                   autoComplete="name"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="address">Address</Label>
                            <Input type="text"
                                   id="address"
                                   name="address"
                                   value={item.address || ''}
                                   onChange={this.handleChange}
                                   placeholder="Address"
                                   autoComplete="address"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="city">City</Label>
                            <Input type="text"
                                   id="city"
                                   name="city"
                                   value={item.city || ''}
                                   onChange={this.handleChange}
                                   placeholder="City"
                                   autoComplete="city"/>
                        </FormGroup>
                        <div className="row">
                            <FormGroup className="col-md-4 mb-3">
                                <Label for="stateOrProvince">State/Province</Label>
                                <Input type="text"
                                       id="stateOrProvince"
                                       name="stateOrProvince"
                                       value={item.stateOrProvince || ''}
                                       onChange={this.handleChange}
                                       placeholder="State/Province"
                                       autoComplete="address-level1"/>
                            </FormGroup>
                            <FormGroup className="col-md-5 mb-3">
                                <Label for="country">Country</Label>
                                <Input type="text"
                                       id="country"
                                       name="country"
                                       value={item.country || ''}
                                       onChange={this.handleChange}
                                       placeholder="Country"
                                       autoComplete="address-level1"/>
                            </FormGroup>
                            <FormGroup className="col-md-3 mb-3">
                                <Label for="postalCode">Postal Code</Label>
                                <Input type="text"
                                       id="postalCode"
                                       name="postalCode"
                                       value={item.postalCode || ''}
                                       onChange={this.handleChange}
                                       placeholder="Postal Code"
                                       autoComplete="address-level1"/>
                            </FormGroup>
                        </div>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/groups">Cancel</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }

}

export default GroupEdit;