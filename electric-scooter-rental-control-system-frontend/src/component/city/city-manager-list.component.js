import React, {Component} from "react";
import {Link} from "react-router-dom";
import ScooterModelService from "../../service/scooter-model.service"
import CityService from "../../service/city.service"

export default class CityManagerList extends Component {
    constructor(props) {
        super(props);
        this.retrieveCities = this.retrieveCities.bind(this);
        this.setActiveCity = this.setActiveCity.bind(this);

        this.state = {
            cities: [],
            currentCity: null,
            currentIndex: -1
        };
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
        CityService.getById(city.id)
            .then(response => {
                this.setState({
                    currentCity: response.data,
                    currentIndex: index
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { cities, currentCity, currentIndex} = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>All cities</h4><br/>

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
                    <Link
                        to={"/add-city"}
                        className="btn btn-primary btn-block updateButton"
                    >
                        Add city
                    </Link>
                </div>
                <div className="col-md-6">
                    {currentCity ? (
                        <div><br/><br/>
                            <h4>City</h4><br/>
                            <div>
                                <strong>Name: </strong>{currentCity.name}
                            </div><br/>
                            <Link
                                to={"/cities/" + currentCity.id}
                                className="btn btn-primary btn-block editButton"
                            >
                                Edit
                            </Link>
                        </div>

                    ) : (
                        <div>
                            <br />
                            <p>Please click on city</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

