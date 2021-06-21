import React, {Component} from "react";
import {Link} from "react-router-dom";
import RentalPointService from "../../service/rental-point.service"
import CityService from "../../service/city.service"

export default class UserRentalPointList extends Component {
    constructor(props) {
        super(props);
        this.retrieveRentalPoints = this.retrieveRentalPoints.bind(this);
        this.setActiveRentalPoint = this.setActiveRentalPoint.bind(this);

        this.state = {
            rentalPoints: [],
            city: [],
            currentRentalPoint: null,
            street: "",
            buildingNumber: 0
        };
    }

    componentDidMount() {
        this.retrieveRentalPoints(this.props.match.params.id);
    }

    retrieveRentalPoints(id) {
        RentalPointService.getAllByCity(id)
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

    setActiveRentalPoint(rentalPoint, index) {
        CityService.getById(rentalPoint.cityId)
            .then(response => {
                this.setState({
                    city: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
        RentalPointService.getById(rentalPoint.id)
            .then(response => {
                this.setState({
                    currentRentalPoint: response.data,
                    currentIndex: index,
                    city: this.state.city
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { rentalPoints, currentRentalPoint, currentIndex, city} = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>Rental points</h4><br/>

                    <ul className="list-group">
                        {rentalPoints &&
                        rentalPoints.map((rentalPoint, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveRentalPoint(rentalPoint, index)}
                                key={index}
                            >
                                st. {rentalPoint.street}, {rentalPoint.buildingNumber}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-md-6">
                    {currentRentalPoint ? (
                        <div><br/><br/>
                            <h4>Scooter rental point</h4><br/>
                            <div>
                                <strong>Street: </strong>{currentRentalPoint.street}
                            </div><br/>
                            <div>
                                <strong>Building number: </strong>{currentRentalPoint.buildingNumber}
                            </div><br/>
                            <div>
                                <strong>City: </strong>{city==null ? "" : city.name}
                            </div><br/>
                            <Link
                                to={"/scooters-for-user/" + currentRentalPoint.id}
                            >
                                Availability of scooters
                            </Link><br/>
                        </div>

                    ) : (
                        <div>
                            <br />
                            <p>Please click on rental point</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

