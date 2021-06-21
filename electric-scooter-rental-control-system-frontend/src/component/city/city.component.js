import React, {Component} from "react";
import CityService from "../../service/city.service"

export default class City extends Component {
    constructor(props) {
        super(props);
        this.getCity = this.getCity.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.updateCity = this.updateCity.bind(this);
        this.deleteCity = this.deleteCity.bind(this);

        this.state = {
            currentCity: {
                id: null,
                name: ""
            },
            message: ""
        };
    }

    componentDidMount() {
        this.getCity(this.props.match.params.id);
    }

    getCity(id) {
        CityService.getById(id)
            .then(response => {
                this.setState({
                    currentCity: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeName(e) {
        const newName = e.target.value;
        this.setState(function(prevState) {
            return {
                currentCity: {
                    ...prevState.currentCity,
                    name: newName
                }
            };
        });
    }

    updateCity() {
        CityService.update(
            this.state.currentCity
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The city was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteCity() {
        CityService.delete(this.state.currentCity.id)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The city was deleted successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentCity } = this.state;
        return (
            <div>
                {currentCity ? (
                    <div className="edit-form">
                        <h4>City</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="description">Name: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="description"
                                    value={currentCity.name}
                                    onChange={this.onChangeName}
                                />
                            </div>
                        </form>
                        <button
                            className="btn btn-primary btn-block deleteButton"
                            onClick={this.deleteCity}
                        >
                            Delete
                        </button>
                        <button
                            type="submit"
                            className="btn btn-primary btn-block updateButton"
                            onClick={this.updateCity}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a city</p>
                    </div>
                )}
            </div>
        );
    }
}