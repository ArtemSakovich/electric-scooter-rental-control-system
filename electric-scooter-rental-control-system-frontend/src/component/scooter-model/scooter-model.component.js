import React, {Component} from "react";
import ScooterModelService from "../../service/scooter-model.service"

export default class ScooterModel extends Component {
    constructor(props) {
        super(props);
        this.getScooterModel = this.getScooterModel.bind(this);
        this.onChangeTitle = this.onChangeTitle.bind(this);
        this.updateScooterModel = this.updateScooterModel.bind(this);
        this.deleteScooterModel = this.deleteScooterModel.bind(this);

        this.state = {
            currentScooterModel: {
                id: null,
                title: ""
            },
            message: ""
        };
    }

    componentDidMount() {
        this.getScooterModel(this.props.match.params.id);
    }

    getScooterModel(id) {
        ScooterModelService.getById(id)
            .then(response => {
                this.setState({
                    currentScooterModel: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeTitle(e) {
        const newTitle = e.target.value;
        this.setState(function(prevState) {
            return {
                currentScooterModel: {
                    ...prevState.currentScooterModel,
                    title: newTitle
                }
            };
        });
    }

    updateScooterModel() {
        ScooterModelService.update(
            this.state.currentScooterModel
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The model was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteScooterModel() {
        ScooterModelService.delete(this.state.currentScooterModel.id)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The model was deleted successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentScooterModel} = this.state;
        return (
            <div>
                {currentScooterModel ? (
                    <div className="edit-form">
                        <h4>Scooter model</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="description">Title: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="description"
                                    value={currentScooterModel.title}
                                    onChange={this.onChangeTitle}
                                />
                            </div>
                        </form>
                        <button
                            className="btn btn-primary btn-block deleteButton"
                            onClick={this.deleteScooterModel}
                        >
                            Delete
                        </button>
                        <button
                            type="submit"
                            className="btn btn-primary btn-block updateButton"
                            onClick={this.updateScooterModel}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a scooter model</p>
                    </div>
                )}
            </div>
        );
    }
}