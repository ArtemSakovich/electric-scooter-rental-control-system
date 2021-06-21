import React, {Component} from "react";
import CityService from "../../service/city.service"

export default class CityAdd extends Component {
    constructor(props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.addCity = this.addCity.bind(this);

        this.state = {
            id: null,
            name: "",
            message: ""
        };
    }

    componentDidMount() {
    }

    onChangeName(e) {
        this.setState({
            name: e.target.value
        });
    }

    addCity() {
        var data = {
            name: this.state.name
        }
        CityService.addCity(data)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The city was added successfully!"
                });
            })
            .catch(e => {
                console.log(e);
                this.setState({
                    message: "You don't have enough permissions!"
                });
            });
    }

    render() {
        return (
            <div>
                <div className="edit-form">
                    <h4>City</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="description">Name: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.name}
                                onChange={this.onChangeName}
                            />
                        </div>
                    </form>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block updateButton"
                        onClick={this.addCity}
                    >
                        Save
                    </button><br/>
                    {this.state.message !== "" ? (
                        <div className="errorDiv">
                            <strong>{this.state.message}</strong>
                        </div>
                    ) : <div></div>
                    }
                </div>
            </div>
        );
    }
}