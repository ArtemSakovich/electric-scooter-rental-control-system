import React, {Component} from "react";
import {Link} from "react-router-dom";
import UserService from "../../service/user.service"
import RoleService from "../../service/role.service"

export default class SubscriptionList extends Component {
    constructor(props) {
        super(props);
        this.retrieveUsers = this.retrieveUsers.bind(this);
        this.setActiveUser = this.setActiveUser.bind(this);

        this.state = {
            users: [],
            currentUser: null,
            currentIndex: -1,
            role: null
        };
    }

    componentDidMount() {
        this.retrieveUsers();
    }

    retrieveUsers() {
        UserService.getAll()
            .then(response => {
                this.setState({
                    users: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    setActiveUser(user, index) {
        RoleService.getById(user.roleId)
            .then(response => {
                this.setState({
                    role: response.data,
                    currentUser: user,
                    currentIndex: index
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { users, currentUser, currentIndex, role} = this.state;
        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>All users</h4><br/>

                    <ul className="list-group">
                        {users &&
                        users.map((user, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveUser(user, index)}
                                key={index}
                            >
                                {user.email}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-md-6">
                    {currentUser ? (
                        <div><br/><br/>
                            <h4>User</h4><br/>
                            <div>
                                <strong>Role: </strong>{role==null ? "" : role.name}
                            </div><br/>
                            <div>
                                <strong>Email: </strong>{currentUser.email}
                            </div><br/>
                            <div>
                                <strong>Name: </strong>{currentUser.name}
                            </div><br/>
                            <div>
                                <strong>Surname: </strong>{currentUser.surname}
                            </div><br/>
                            <div>
                                <strong>Age: </strong>{currentUser.age}
                            </div><br/>
                            <Link
                                to={"/users/" + currentUser.id}
                                className="btn btn-primary btn-block editButton"
                            >
                                Edit
                            </Link>
                        </div>

                    ) : (
                        <div>
                            <br />
                            <p>Please click on user</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

