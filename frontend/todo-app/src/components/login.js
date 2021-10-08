import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';



const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
      paddingTop: '25ch',
      textAlign: 'center',
      width: '100%',
      
    },
    button: {
        backgroundColor: 'lightblue'
    },
    loginFail: {
        width:'100%', 
        textAlign: 'center'
    },
    nav: {
        backgroundColor: 'lightblue'
    }
    
  }));

function Login(props) {
    const classes = useStyles();
    const history = useHistory();
    const [loginId, setLoginId] = useState("");
    const [password, setPassword] = useState("");
    const [loginFail, setLoginFail] = useState(false);
  
    const handleSubmit = (evt) => {
        evt.preventDefault();
        /*
        AuthenticationService.executeJWTAuthenticationService(loginId, password)
        .then((response) => {
            AuthenticationService.registerSuccessfulLoginforJwt(loginId, response.data.token);
            setLoginFail(false);
            history.push(`/welcome/${loginId}`);
        }).catch( () => {
            setLoginFail(true);
        })*/
        setLoginFail(false);
        history.push(`/welcome/${loginId}`);
    }
    return (
    <div className={classes.App}>
        <form className={classes.root} noValidate autoComplete="off" onSubmit={handleSubmit}>

            {loginFail && <div className={classes.loginFail}>Invalid Credentials</div>}
            <TextField 
                id="loginId" 
                label="Login ID" 
                variant="outlined"
                value={loginId}
                onChange={e => setLoginId(e.target.value)}
                /> <br/>
            <TextField 
                id="password" 
                label="Password" 
                variant="outlined" 
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                /> <br/>
            <Button type="submit" className={classes.button} variant="contained"  >
                Submit
            </Button>
        </form>
        
      
    </div>
  );
}

export default Login;

