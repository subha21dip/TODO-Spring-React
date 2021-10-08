import React, {useState} from 'react';



function Welcome(props) {

  return (
    <div className="App">
      <h3 style={{padding:'15px'}}><b>Welcome {props.match.params.name}!</b></h3><br/>
      <p></p>
      <div style={{textAlign:'center'}}>
        <p>This application is a WIP.</p> <br/> <p>Click once on the icon in navigation bar to get started!</p>
        <p><b>Stack:</b>  Spring Boot - React JS</p> <br/>
      </div>
    </div>
  );
}

export default Welcome;

