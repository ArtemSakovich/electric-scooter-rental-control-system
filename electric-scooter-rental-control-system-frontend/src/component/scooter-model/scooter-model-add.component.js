import React, {Component} from "react";
import ScooterModelService from "../../service/scooter-model.service"

export default class ScooterModelAdd extends Component {
    constructor(props) {
        super(props);
        this.onChangeTitle = this.onChangeTitle.bind(this);
        this.addScooterModel = this.addScooterModel.bind(this);

        this.state = {
            id: null,
            title: "",
            message: ""
        };
    }

    componentDidMount() {
    }

    onChangeTitle(e) {
        this.setState({
            title: e.target.value
        });
    }

    addScooterModel() {
        var data = {
            title: this.state.title
        }
        ScooterModelService.addModel(data)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The model was added successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        return (
            <div>
                <div className="edit-form">
                    <h4>Scooter model</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="description">Title: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.title}
                                onChange={this.onChangeTitle}
                            />
                        </div>
                    </form>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block updateButton"
                        onClick={this.addScooterModel}
                    >
                        Save
                    </button>
                    <p>{this.state.message}</p>
                </div>
            </div>
        );
    }
}