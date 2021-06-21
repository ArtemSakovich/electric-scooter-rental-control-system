import React, { Component } from "react";
import ScooterService from "../../service/scooter.service"
import ScooterModelService from "../../service/scooter-model.service"
import RentalPointService from "../../service/rental-point.service"

export default class Scooter extends Component {
    constructor(props) {
        super(props);
        this.onChangeScooterModel = this.onChangeScooterModel.bind(this);
        this.onChangeScooterStatus = this.onChangeScooterStatus.bind(this);
        this.onChangePricePerHour = this.onChangePricePerHour.bind(this);
        this.onChangePassedHours = this.onChangePassedHours.bind(this);
        this.onChangeRentalPoint = this.onChangeRentalPoint.bind(this);
        this.getScooterModels = this.getScooterModels.bind(this);
        this.getRentalPoints = this.getRentalPoints.bind(this);
        this.getScooter = this.getScooter.bind(this);
        this.updateScooter = this.updateScooter.bind(this);
        this.deleteScooter = this.deleteScooter.bind(this);

        this.state = {
            currentScooter: {
                id: null,
                modelId: null,
                status: "",
                pricePerHour: 0.0,
                passedHours: 0,
                scooterRentalPointId: null
            },
            scooterModels: [],
            rentalPoints: [],
            message: ""
        };
    }

    componentDidMount() {
        this.getScooterModels();
        this.getRentalPoints();
        this.getScooter(this.props.match.params.id);
    }

    getScooter(id) {
        ScooterService.getScooter(id)
            .then(response => {
                this.setState({
                    currentScooter: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    getScooterModels() {
        ScooterModelService.getAll()
            .then(response => {
                this.setState({
                    scooterModels: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    getRentalPoints() {
        RentalPointService.getAll()
            .then(response => {
                this.setState({
                    rentalPoints: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeScooterModel(e) {
        const scooterModel = e.target.value;
        console.log(e.target.value)
        this.setState(function(prevState) {
            return {
                currentScooter: {
                    ...prevState.currentScooter,
                    modelId: scooterModel
                }
            };
        });
    }

    onChangeScooterStatus(e) {
        const scooterStatus = e.target.value;

        this.setState(prevState => ({
            currentScooter: {
                ...prevState.currentScooter,
                status: scooterStatus
            }
        }));
    }

    onChangePricePerHour(e) {
        const newPricePerHour = e.target.value;
        this.setState(prevState => ({
            currentScooter: {
                ...prevState.currentScooter,
                pricePerHour: newPricePerHour
            }
        }));
        console.log(this.state)
    }

    onChangePassedHours(e) {
        const newPassedHours = e.target.value;
        this.setState(prevState => ({
            currentScooter: {
                ...prevState.currentScooter,
                passedHours: newPassedHours
            }
        }));
    }

    onChangeRentalPoint(e) {
        const rentalPoint = e.target.value;

        this.setState(prevState => ({
            currentScooter: {
                ...prevState.currentScooter,
                scooterRentalPointId: rentalPoint
            }
        }));
    }

    updateScooter() {
        ScooterService.update(
            this.state.currentScooter
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The scooter was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteScooter() {
        ScooterService.delete(this.state.currentScooter.id)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The scooter was deleted successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentScooter, scooterModels, rentalPoints} = this.state;
console.log("current scooter " + currentScooter)
        return (
            <div>
                {currentScooter ? (
                    <div className="edit-form">
                        <h4>Scooter</h4>
                        <form>
                            <div className="form-group">
                                <label>Scooter model: </label>
                                <select onChange={this.onChangeScooterModel} id="scooterModelSelector">
                                        {scooterModels &&
                                        scooterModels.map((scooterModel) => (
                                                <option selected={currentScooter.modelId === scooterModel.id}
                                                        value={scooterModel.id}>{scooterModel.title}
                                                    </option>
                                        ))}
                                </select>
                            </div>
                            <div className="form-group">
                                <label htmlFor="description">Status:</label>
                                <select onChange={this.onChangeScooterStatus} id="statusSelector">
                                    <option selected={currentScooter.status === 'AVAILABLE'}
                                            value="AVAILABLE">Available</option>
                                    <option selected={currentScooter.status === 'CHARGING'}
                                            value="CHARGING">Charging</option>
                                    <option selected={currentScooter.status === 'BUSY'}
                                            value="BUSY">Busy</option>
                                    <option selected={currentScooter.status === 'FAULTY'}
                                            value="FAULTY">Faulty</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label htmlFor="description">Price per hour: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="description"
                                    value={currentScooter.pricePerHour}
                                    onChange={this.onChangePricePerHour}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Mileage in hours: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentScooter.passedHours}
                                    onChange={this.onChangePassedHours}
                                />
                            </div>
                            <div className="form-group">
                                <label>Rental point: </label>
                                <select onChange={this.onChangeRentalPoint} id="rentalPointSelector">
                                        {rentalPoints &&
                                        rentalPoints.map((rentalPoint) => (
                                                <option selected={currentScooter.scooterRentalPointId === rentalPoint.id}
                                                    value={rentalPoint.id}>st. {rentalPoint.street}, {rentalPoint.buildingNumber}</option>
                                        ))}
                                </select>
                            </div>
                        </form>
                        <button
                            className="btn btn-primary btn-block deleteButton"
                            onClick={this.deleteScooter}
                        >
                            Delete
                        </button>
                        <button
                            type="submit"
                            className="btn btn-primary btn-block updateButton"
                            onClick={this.updateScooter}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a scooter...</p>
                    </div>
                )}
            </div>
        );
    }
}