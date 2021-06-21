import React, { Component } from "react";
import {Link, Route, Switch} from "react-router-dom"
import CityService from "../../service/city.service"
import RentalPointService from "../../service/rental-point.service";

export default class CityList extends Component {
    constructor(props) {
        super(props);
        this.retrieveCities = this.retrieveCities.bind(this);
        this.setActiveCity = this.setActiveCity.bind(this);

        this.state = {
            cities: [],
            rentalPoints: [],
            currentCity: null,
            currentIndex: -1,
        }
        ;
    }

    componentDidMount() {
        this.retrieveCities();
    }

    retrieveCities() {
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

    setActiveCity(city, index) {
        RentalPointService.getAllByCity(city.id)
            .then(response => {
                this.setState({
                    rentalPoints: response.data,
                    currentCity: city,
                    currentIndex: index
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { cities, rentalPoints, currentCity, currentIndex } = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>Where can you visit us?</h4><br/>
                    <ul className="list-group">
                        {cities &&
                        cities.map((city, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveCity(city, index)}
                                key={index}
                            >
                                {city.name}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-md-6">
                    {currentCity ? (
                        <div>
                            <div className={"rounded-list"}><br/><br/>
                                <strong>Rental points:</strong><br/>
                                <ol>
                                    {rentalPoints &&
                                    rentalPoints.map((rentalPoint, index) => (
                                        <li>
                                            <p>st. {rentalPoint.street}, {rentalPoint.buildingNumber}</p>
                                        </li>
                                    ))}
                                </ol>
                            </div>
                        </div>

                    ) : (
                        <div>
                            <br/>
                            <br/>
                            <p>Please click on a city</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

