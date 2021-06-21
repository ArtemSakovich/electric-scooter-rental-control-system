import React, {Component} from "react";
import CityService from "../../service/city.service";
import ScooterRentalPointService from "../../service/rental-point.service"

const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

export default class RentalPointAdd extends Component {
    constructor(props) {
        super(props);
        this.getCities = this.getCities.bind(this);
        this.onChangeStreet = this.onChangeStreet.bind(this);
        this.onChangeBuildingNumber = this.onChangeBuildingNumber.bind(this);
        this.onChangeCity = this.onChangeCity.bind(this);
        this.addRentalPoint = this.addRentalPoint.bind(this);

        this.state = {
            id: null,
            street: "",
            buildingNumber: 0,
            cityId: 0.0,
            cities: [],
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

    onChangeStreet(e) {
        this.setState({
            street: e.target.value
        });
    }

    onChangeBuildingNumber(e) {
        this.setState({
            buildingNumber: e.target.value
        });
    }

    onChangeCity(e) {
        this.setState({
            cityId: e.target.value
        });
    }

    addRentalPoint() {
        var data = {
            street: this.state.street,
            buildingNumber: this.state.buildingNumber,
            cityId: this.state.cityId
        }
        ScooterRentalPointService.addScooterRentalPoint(data)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The rental point was added successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {cities} = this.state;

        return (
            <div>
                <div className="edit-form">
                    <h4>Scooter rental point</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="description">Street: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.street}
                                onChange={this.onChangeStreet}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Building number: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.buildingNumber}
                                onChange={this.onChangeBuildingNumber}
                            />
                        </div>
                        <div className="form-group">
                            <label>City: </label>
                            <select required={true} onChange={this.onChangeCity} id="rentalPointSelector">
                                <option hidden="" disabled="disabled" selected="selected" value="">Please select city</option>
                                {cities &&
                                cities.map((city) => (
                                    <option
                                        value={city.id}>{city.name}</option>
                                ))}
                            </select>
                        </div>
                    </form>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block updateButton"
                        onClick={this.addRentalPoint}
                    >
                        Save
                    </button>
                    <p>{this.state.message}</p>
                </div>
            </div>
        );
    }
}