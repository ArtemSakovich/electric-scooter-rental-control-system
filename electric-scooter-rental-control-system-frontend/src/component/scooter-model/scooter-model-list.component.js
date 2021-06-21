import React, {Component} from "react";
import {Link} from "react-router-dom";
import ScooterModelService from "../../service/scooter-model.service"

export default class RentalPointList extends Component {
    constructor(props) {
        super(props);
        this.retrieveScooterModels = this.retrieveScooterModels.bind(this);
        this.setActiveScooterModel = this.setActiveScooterModel.bind(this);

        this.state = {
            scooterModels: [],
            currentScooterModel: null,
            currentIndex: -1
        };
    }

    componentDidMount() {
        this.retrieveScooterModels();
    }

    retrieveScooterModels() {
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

    setActiveScooterModel(scooterModel, index) {
        ScooterModelService.getById(scooterModel.id)
            .then(response => {
                this.setState({
                    currentScooterModel: response.data,
                    currentIndex: index
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { scooterModels, currentScooterModel, currentIndex} = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>All scooter models</h4><br/>

                    <ul className="list-group">
                        {scooterModels &&
                        scooterModels.map((scooterModel, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveScooterModel(scooterModel, index)}
                                key={index}
                            >
                                {scooterModel.title}
                            </li>
                        ))}
                    </ul>
                    <Link
                        to={"/add-scooter-model"}
                        className="btn btn-primary btn-block updateButton"
                    >
                        Add model
                    </Link>
                </div>
                <div className="col-md-6">
                    {currentScooterModel ? (
                        <div><br/><br/>
                            <h4>Scooter model</h4><br/>
                            <div>
                                <strong>Title: </strong>{currentScooterModel.title}
                            </div><br/>
                            <Link
                                to={"/scooter-models/" + currentScooterModel.id}
                                className="btn btn-primary btn-block editButton"
                            >
                                Edit
                            </Link>
                        </div>

                    ) : (
                        <div>
                            <br />
                            <p>Please click on scooter model</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

