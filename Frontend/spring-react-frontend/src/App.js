import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle'
import Navbar from "./components/Navbar/Navbar";
import AuthContext from "./context/AuthContext";
import Login from "./components/auth/login/Login";
import Signup from "./components/auth/signup/Signup";
import {BrowserRouter, Route, Routes} from "react-router-dom";
function App() {
  return (
      <div className="App">
              <BrowserRouter>
                  <AuthContext>
                  <Navbar/>
                  <Routes>
                      <Route path={"/login"} element={<Login/>}/>
                      <Route path={"/signUp"} element={<Signup/>}/>
                  </Routes>
                  </AuthContext>
              </BrowserRouter>
      </div>
  );
}

export default App;
