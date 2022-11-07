import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle";
import Navbar from "./components/Navbar/Navbar";
import AuthContext from "./context/AuthContext";
import Login from "./components/auth/login/Login";
import Signup from "./components/auth/signup/Signup";
import {Route, Routes} from "react-router-dom";
import ViewPost from "./components/post/view-post/ViewPost";
import CreatePost from "./components/post/create-post/CreatePost";
import MainPage from "./components/HomePage/main-page/MainPage";
import ApiContext from "./context/ApiContext";
import CreatePostPrivateRoute from "./components/auth/PrivateRouting/CreatePostPrivateRoute";

function App() {
    return (
        <div className="App">
            <ApiContext>
                <AuthContext>
                    <Navbar/>
                    <Routes>
                        <Route path={"/"} element={<MainPage/>}/>
                        <Route path={"/login"} element={<Login/>}/>
                        <Route path={"/signUp"} element={<Signup/>}/>
                        <Route path={"/viewPost"} element={<ViewPost/>}/>
                        <Route path={"/createPost"} element={
                            <CreatePostPrivateRoute>
                                <CreatePost/>
                            </CreatePostPrivateRoute>
                        }/>
                    </Routes>
                </AuthContext>
            </ApiContext>
        </div>
    );
}

export default App;
