import React from "react";
import { Route, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";

// jedno sranje, al jebiga, to ti je buucnost
const SecuredRoute = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={props =>
      security.validToken === true ? (
        <Component {...otherProps} />
      ) : (
        <Redirect to="login" />
      )
    }
  />
);

SecuredRoute.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(SecuredRoute);
