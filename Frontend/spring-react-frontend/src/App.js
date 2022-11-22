import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle";
import Navbar from "./components/Navbar/Navbar";
import AuthContext from "./context/AuthContext";
import Login from "./components/auth/login/Login";
import Signup from "./components/auth/signup/Signup";
import { Route, Routes } from "react-router-dom";
import ViewPost from "./components/post/view-post/ViewPost";
import CreatePost from "./components/post/create-post/CreatePost";
import MainPage from "./components/HomePage/main-page/MainPage";
import ApiContext from "./context/ApiContext";
import CreatePostPrivateRoute from "./components/auth/PrivateRouting/CreatePostPrivateRoute";
import { ToastContainer } from "react-toastify";
import SignPrivateRoute from "./components/auth/PrivateRouting/SignPrivateRoute";
import LoginAndAuth from "./components/auth/PrivateRouting/LoginAndAuth";
import VerificationPage from "./components/auth/verify/VerificationPage";
import PreventMainPage from "./components/auth/PrivateRouting/PreventMainPage";

function App() {
  return (
    <div className="App">
      <AuthContext.Provider
        value={{
          username:localStorage.getItem('username'),
          isLogin:JSON.parse(localStorage.getItem('isLogin')),
          jwtToken:localStorage.getItem('jwtToken'),
          isVerified:false
        }}
      >
      <ApiContext>
          <ToastContainer/>
          <Navbar />

          <Routes>
            <Route path={"/"}
              element={
                <LoginAndAuth>
                  <MainPage/>
                </LoginAndAuth>
              }
            />
            <Route path={"/mainPage"} element={
                <MainPage/>
            }/>
            <Route path={"/login"} element={
              <SignPrivateRoute>
                <Login />
              </SignPrivateRoute>
            } />
            <Route path={"/verify"} element={

              <VerificationPage/>
            }/>
            <Route path={"/signUp"} element={              
              <SignPrivateRoute>
                <Signup/>
              </SignPrivateRoute>} />
            <Route path={"/viewPost/:id"} element={<ViewPost />} />
            <Route
              path={"/createPost"}
              element={
                <CreatePostPrivateRoute>
                  <CreatePost />
                </CreatePostPrivateRoute>
              }
            />
          </Routes>
      </ApiContext>
      </AuthContext.Provider>
    </div>
  );
}

export default App;
