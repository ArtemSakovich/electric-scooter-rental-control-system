import React, { Component } from "react";
import AuthService from "../../service/auth.service";
import {Link} from "react-router-dom";

export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: AuthService.getCurrentUser()
        };
    }

    render() {
        const { currentUser } = this.state;
        return (
            <div className="col-md-12">
                <div className="card card-container">
                <header className="jumbotron">
                    <img
                        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                        alt="profile-img"
                        className="profile-img-card"
                    /><br/>
                    <h4>
                        <strong>{currentUser.email}</strong>
                    </h4>
                </header><br/><br/><br/>
                <p>
                    <strong>Name:</strong>{" "}
                    {currentUser.name}
                </p>
                <p>
                    <strong>Surname:</strong>{" "}
                    {currentUser.surname}
                </p>
                <p>
                    <strong>Age:</strong>{" "}
                    {currentUser.age}
                </p>
                <p>
                    <strong>Role:</strong>{" "}
                    {currentUser.role.substring(currentUser.role.indexOf('_') + 1, currentUser.role.indexOf(']'))}
                </p>
                    <Link
                        to={"/edit-profile/" + currentUser.id}
                        className="btn btn-primary btn-block editButton"
                    >
                        Edit
                    </Link>
                </div>
            </div>
        );
    }
}


