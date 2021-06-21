import React, {Component} from "react";
import UserService from "../../service/user.service";


export default class UpdateUserProfile extends Component {
    constructor(props) {
        super(props);
        this.getUser = this.getUser.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeSurname = this.onChangeSurname.bind(this);
        this.onChangeAge = this.onChangeAge.bind(this);
        this.updateUser = this.updateUser.bind(this);

        this.state = {
            currentUser: {
                id: null,
                email: "",
                name: "",
                surname: "",
                age: 0.0
            },
            roles: [],
            message: ""
        };
    }

    componentDidMount() {
        this.getUser(this.props.match.params.id);
    }

    getUser(id) {
        UserService.getById(id)
            .then(response => {
                this.setState({
                    currentUser: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeEmail(e) {
        const newEmail = e.target.value;
        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                email: newEmail
            }
        }));
        console.log(this.state)
    }

    onChangeName(e) {
        const newName = e.target.value;
        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                name: newName
            }
        }));
    }

    onChangeSurname(e) {
        const newSurname = e.target.value;

        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                surname: newSurname
            }
        }));
    }

    onChangeAge(e) {
        const newAge = e.target.value;

        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                age: newAge
            }
        }));
    }

    updateUser() {
        UserService.updateWithoutRole(
            this.state.currentUser
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentUser} = this.state;
        return (
            <div>
                {currentUser ? (
                    <div className="edit-form">
                        <h4>User</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="description">Email: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="description"
                                    value={currentUser.email}
                                    onChange={this.onChangeEmail}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Name: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentUser.name}
                                    onChange={this.onChangeName}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Surname: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentUser.surname}
                                    onChange={this.onChangeSurname}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Age: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentUser.age}
                                    onChange={this.onChangeAge}
                                />
                            </div>
                        </form>
                        <button
                            type="submit"
                            className="btn btn-primary btn-block updateButton"
                            onClick={this.updateUser}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a user...</p>
                    </div>
                )}
            </div>
        );
    }
}