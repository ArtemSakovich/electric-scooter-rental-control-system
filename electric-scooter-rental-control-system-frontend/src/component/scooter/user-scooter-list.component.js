import React, {Component} from "react";
import ScooterService from "../../service/scooter.service"
import ScooterModelService from "../../service/scooter-model.service"
import RentalPointService from "../../service/rental-point.service"

export default class UserScooterList extends Component {
    constructor(props) {
        super(props);
        this.retrieveScooters = this.retrieveScooters.bind(this);
        this.setActiveScooter = this.setActiveScooter.bind(this);

        this.state = {
            scooters: [],
            currentScooter: null,
            currentIndex: -1,
            rentalPoint: null,
            scooterModel: null
        };
    }

    componentDidMount() {
        this.retrieveScooters(this.props.match.params.id);
    }

    retrieveScooters(id) {
        ScooterService.getAllByRentalPoint(id)
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

    setActiveScooter(scooter, index) {
        ScooterModelService.getById(scooter.modelId)
            .then(response => {
                this.setState({
                    scooterModel: response.data,
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
        RentalPointService.getById(scooter.scooterRentalPointId)
            .then(response => {
                this.setState({
                    rentalPoint: response.data,
                    currentScooter: scooter,
                    currentIndex: index,
                    scooterModel: this.state.scooterModel,
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { scooters, currentScooter, currentIndex, rentalPoint, scooterModel} = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>All scooters</h4><br/>

                    <ul className="list-group">
                        {scooters &&
                        scooters.map((scooter, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveScooter(scooter, index)}
                                key={index}
                            >
                                Price: {scooter.pricePerHour} BYN <strong>- {scooter.status}</strong>
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-md-6">
                    <p>Amount of scooters: {scooters.length}</p>
                    {currentScooter ? (
                        <div><br/><br/>
                            <h4>Scooter</h4><br/>
                            <div>
                                <strong>Model: </strong>{scooterModel==null ? "" : scooterModel.title}
                            </div><br/>
                            <div>
                                <strong>Status: </strong>{currentScooter.status}
                            </div><br/>
                            <div>
                                <strong>Price per hour: </strong>{currentScooter.pricePerHour} BYN
                            </div><br/>
                            <div>
                                <strong>Mileage: </strong>{currentScooter.passedHours} hours
                            </div><br/>
                            <div>
                                <strong>Rental point: </strong>st. {rentalPoint.street}, {rentalPoint.buildingNumber}
                            </div><br/>
                        </div>
                    ) : (
                        <div>
                            <br />
                            <p>Please click on scooter</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

