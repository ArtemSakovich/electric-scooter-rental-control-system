import React, { Component } from "react";
import ScooterService from "../../service/scooter.service"
import ScooterModelService from "../../service/scooter-model.service"
import RentalPointService from "../../service/rental-point.service"

export default class ScooterAdd extends Component {
    constructor(props) {
        super(props);
        this.onChangeScooterModel = this.onChangeScooterModel.bind(this);
        this.onChangeScooterStatus = this.onChangeScooterStatus.bind(this);
        this.onChangePricePerHour = this.onChangePricePerHour.bind(this);
        this.onChangePassedHours = this.onChangePassedHours.bind(this);
        this.onChangeRentalPoint = this.onChangeRentalPoint.bind(this);
        this.getScooterModels = this.getScooterModels.bind(this);
        this.getRentalPoints = this.getRentalPoints.bind(this);
        this.addScooter = this.addScooter.bind(this);

        this.state = {
            id: null,
            modelId: null,
            status: "",
            pricePerHour: 0.0,
            passedHours: 0,
            scooterRentalPointId: null,
            scooterModels: [],
            rentalPoints: [],
            message: ""
        };
    }

    componentDidMount() {
        this.getScooterModels();
        this.getRentalPoints();
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
        this.setState({
            modelId: e.target.value
        });
    }

    onChangeScooterStatus(e) {
        this.setState({
            status: e.target.value
        });
    }

    onChangePricePerHour(e) {
        this.setState({
            pricePerHour: e.target.value
        });
    }

    onChangePassedHours(e) {
        this.setState({
            passedHours: e.target.value
        });
    }

    onChangeRentalPoint(e) {
        this.setState({
            scooterRentalPointId: e.target.value
        });
    }

    addScooter() {
        var data = {
            modelId: this.state.modelId,
            status: this.state.status,
            pricePerHour: this.state.pricePerHour,
            passedHours: this.state.passedHours,
            scooterRentalPointId: this.state.scooterRentalPointId
        }
        console.log("hello")
        console.log(data)
        ScooterService.addScooter(data)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The scooter was added successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {scooterModels, rentalPoints} = this.state;

        return (
            <div>
                <div className="edit-form">
                    <h4>Scooter</h4>
                    <form>
                        <div className="form-group">
                            <label>Scooter model: </label>
                            <select onChange={this.onChangeScooterModel} id="scooterModelSelector">
                                <option value="Choose model"/>
                                {scooterModels &&
                                scooterModels.map((scooterModel) => (
                                    <option
                                        value={scooterModel.id}>{scooterModel.title}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Status:</label>
                            <select onChange={this.onChangeScooterStatus} id="statusSelector">
                                <option >Choose status</option>
                                <option
                                     value="AVAILABLE">Available
                                </option>
                                <option
                                    value="CHARGING">Charging
                                </option>
                                <option
                                    value="BUSY">Busy
                                </option>
                                <option
                                    value="FAULTY">Faulty
                                </option>
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Price per hour: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.pricePerHour}
                                onChange={this.onChangePricePerHour}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Mileage in hours: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.passedHours}
                                onChange={this.onChangePassedHours}
                            />
                        </div>
                        <div className="form-group">
                            <label>Rental point: </label>
                            <select onChange={this.onChangeRentalPoint} id="rentalPointSelector">
                                <option value="Choose rental point"/>
                                {rentalPoints &&
                                rentalPoints.map((rentalPoint) => (
                                    <option
                                        value={rentalPoint.id}>st. {rentalPoint.street}, {rentalPoint.buildingNumber}</option>
                                ))}
                            </select>
                        </div>
                    </form>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block updateButton"
                        onClick={this.addScooter}
                    >
                        Save
                    </button>
                    <p>{this.state.message}</p>
                </div>
            </div>
        );
    }
}