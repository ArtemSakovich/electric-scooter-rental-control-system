import React, { Component } from "react";
import ScooterService from "../../service/scooter.service"
import RentalPointService from "../../service/rental-point.service"
import RideService from "../../service/ride.service"
import CityService from "../../service/city.service"
import ScooterRentalPointService from "../../service/rental-point.service"
import {waitFor} from "@testing-library/react";

export default class OrderRide extends Component {
    constructor(props) {
        super(props);
        this.getCities = this.getCities.bind(this);
        this.onChangeScooter = this.onChangeScooter.bind(this);
        this.onChangeRentalPoint = this.onChangeRentalPoint.bind(this);
        this.onChangeReturnRentalPoint = this.onChangeReturnRentalPoint.bind(this);
        this.onChangeRideHours = this.onChangeRideHours.bind(this);
        this.onChangeCity = this.onChangeCity.bind(this);
        this.orderRide = this.orderRide.bind(this);
        this.onChangeScooter = this.onChangeScooter.bind(this);

        this.state = {
            cities: [],
            rentalPoints: [],
            scooterId: null,
            returnScooterRentalPointId: null,
            userId: null,
            scooters: [],
            rideHours: 0,
            message: ""
        };
    }

    componentDidMount() {
        this.getCities();
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

    onChangeRentalPoint(e) {
        ScooterService.getAllAvailableScootersByRentalPointId(e.target.value)
            .then(response => {
                this.setState({
                    scooters: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeCity(e) {
        RentalPointService.getAllByCity(e.target.value)
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

    onChangeScooter(e) {
        this.setState({
            scooterId: e.target.value
        });
    }

    onChangeReturnRentalPoint(e) {
        this.setState({
            returnScooterRentalPointId: e.target.value
        });
    }

    onChangeRideHours(e) {
        this.setState({
            rideHours: e.target.value
        });
    }

    orderRide() {
        var data = {
            userId: this.props.match.params.id,
            scooterId: this.state.scooterId,
            returnScooterRentalPointId: this.state.returnScooterRentalPointId,
            rideHours: this.state.rideHours
        }

        RideService.orderRide(data)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The ride was ordered successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {cities, rentalPoints, scooters} = this.state;

        return (
            <div>
                <div className="edit-form">
                    <h4>Order ride</h4>
                    <form>
                        <div className="form-group">
                            <label>City: </label>
                            <select onChange={this.onChangeCity} id="scooterModelSelector">
                                <option value="Choose model"/>
                                {cities &&
                                cities.map((city) => (
                                    <option
                                        value={city.id}>{city.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label>Rental point: </label>
                            <select onChange={this.onChangeRentalPoint} id="scooterModelSelector">
                                <option value="Choose model"/>
                                {rentalPoints &&
                                rentalPoints.map((rentalPoint) => (
                                    <option
                                        value={rentalPoint.id}>st.{rentalPoint.street}, {rentalPoint.buildingNumber}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label>Scooter: </label>
                            <select onChange={this.onChangeScooter} id="scooterModelSelector">
                                <option value="Choose model"/>
                                {scooters &&
                                scooters.map((scooter) => (
                                    <option
                                        value={scooter.id}>{scooter.status} - Price per hour: {scooter.pricePerHour} BYN; Mileage: {scooter.passedHours} hours
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label>Return rental point: </label>
                            <select onChange={this.onChangeReturnRentalPoint} id="scooterModelSelector">
                                <option value="Choose model"/>
                                {rentalPoints &&
                                rentalPoints.map((rentalPoint) => (
                                    <option
                                        value={rentalPoint.id}>st.{rentalPoint.street}, {rentalPoint.buildingNumber}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Amount of hours: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.rideHours}
                                onChange={this.onChangeRideHours}
                            />
                        </div>
                    </form>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block updateButton"
                        onClick={this.orderRide}
                    >
                        Order
                    </button>
                    <p>{this.state.message}</p>
                </div>
            </div>
        );
    }
}