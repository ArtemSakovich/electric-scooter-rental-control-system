import React, {Component} from "react";
import RentalPointService from "../../service/rental-point.service"
import CityService from "../../service/city.service"

export default class ScooterRentalPoint extends Component {
    constructor(props) {
        super(props);
        this.getRentalPoint = this.getRentalPoint.bind(this);
        this.getCities = this.getCities.bind(this);
        this.onChangeStreet = this.onChangeStreet.bind(this);
        this.onChangeBuildingNumber = this.onChangeBuildingNumber.bind(this);
        this.onChangeCity = this.onChangeCity.bind(this);
        this.deleteRentalPoint = this.deleteRentalPoint.bind(this);
        this.updateRentalPoint = this.updateRentalPoint.bind(this);

        this.state = {
            currentRentalPoint: {
                id: null,
                street: "",
                buildingNumber: 0,
                cityId: null
            },
            cities: [],
            message: ""
        };
    }

    componentDidMount() {
        this.getCities();
        this.getRentalPoint(this.props.match.params.id);
    }

    getRentalPoint(id) {
        RentalPointService.getById(id)
            .then(response => {
                this.setState({
                    currentRentalPoint: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    getCities() {
        CityService.getAll()
            .then(response => {
                this.setState({
                    cities: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeStreet(e) {
        const newStreet = e.target.value;
        this.setState(function(prevState) {
            return {
                currentRentalPoint: {
                    ...prevState.currentRentalPoint,
                    street: newStreet
                }
            };
        });
    }

    onChangeBuildingNumber(e) {
        const newBuildingNumber = e.target.value;
        this.setState(prevState => ({
            currentRentalPoint: {
                ...prevState.currentRentalPoint,
                buildingNumber: newBuildingNumber
            }
        }));
    }

    onChangeCity(e) {
        const newCity = e.target.value;
        this.setState(prevState => ({
            currentRentalPoint: {
                ...prevState.currentRentalPoint,
                cityId: newCity
            }
        }));
        console.log(this.state)
    }

    updateRentalPoint() {
        console.log(this.state.currentRentalPoint)
        RentalPointService.update(
            this.state.currentRentalPoint
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The rental point was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteRentalPoint() {
        RentalPointService.delete(this.state.currentRentalPoint.id)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The rental point was deleted successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentRentalPoint, cities} = this.state;
        return (
            <div>
                {currentRentalPoint ? (
                    <div className="edit-form">
                        <h4>Scooter rental point</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="description">Street: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="description"
                                    value={currentRentalPoint.street}
                                    onChange={this.onChangeStreet}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Building number: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentRentalPoint.buildingNumber}
                                    onChange={this.onChangeBuildingNumber}
                                />
                            </div>
                            <div className="form-group">
                                <label>City: </label>
                                <select onChange={this.onChangeCity} id="rentalPointSelector">
                                    {cities &&
                                    cities.map((city) => (
                                        <option selected={currentRentalPoint.cityId === city.id}
                                                value={city.id}>{city.name}</option>
                                    ))}
                                </select>
                            </div>
                        </form>
                        <button
                            className="btn btn-primary btn-block deleteButton"
                            onClick={this.deleteRentalPoint}
                        >
                            Delete
                        </button>
                        <button
                            type="submit"
                            className="btn btn-primary btn-block updateButton"
                            onClick={this.updateRentalPoint}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a rental point</p>
                    </div>
                )}
            </div>
        );
    }
}