import React, {useCallback, useContext, useEffect, useState} from 'react';
import "./Comment.css"
import profilePic from "../../../images/IMG_20220621_121054-modified.png";
import defaultPic from "../../../images/defaultPic.png"
import {Api} from "../../../context/ApiContext";
import AuthContext from '../../../context/AuthContext';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Axios from "axios";
const MyComponent = ({postId}) => {
    const Auth= useContext(AuthContext);
    const {darkTheme} = useContext(Api);
    const [showRepliedComment,setShowRepliedComment] = useState(false);
    const[commentId,setCommentId] = useState(0);
    const postCommentReplyValues={
        "commentId": 0,
        "userName": "",
        "repliedTo":"",
        "text": "",
        "noOfReplies":0
    }
    const [postCommentValues,setPostCommentValues]=useState({
        postId:0,
        text:"",
        userName:""
    });
    const[commentsData,setCommentsData]=useState([]);
    useEffect(()=>{
        fetch(`http://localhost:8080/api/comments/by-post/${postId}`,{headers: {'Authorization': `Bearer ${Auth.jwtToken}`}})
        .then(function(response) { return response.json(); })
        .then(function(json) {
            setCommentsData(json);
        });
    },[]);
    // To check if user is login then add comment only.
    const handleReplyButtonClick=()=>{
        if (Auth.isLogin && postCommentValues.text.length>0){
            handleAddCommentToPost();
        }
    }

    const handleAddCommentToPost=()=>{
        Axios.post(`http://localhost:8080/api/comments/save`,postCommentValues,{
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+Auth.jwtToken
            }
          }).then((response)=>{
            toast.success('🦄 Comment Added Successfully !!',{
              position: "top-center",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
              });
            setTimeout(RefreshPage,5000);
          }).catch((error)=>{
            console.log("Something went wrong!!")
            console.log(error)
          })
    }
    const RefreshPage=()=>{
        window.location.reload();
      }
    // To handle comment reply.
    const handleCommentReply=()=>{
        console.log(postCommentReplyValues);
    }




    
    return (
        <div className={"Comments"}
             style={darkTheme?{borderColor:"white"}:null}
        >
            <div className="comments-top row">
                <img src={Auth.isLogin?profilePic:defaultPic } alt="" className={"col-md-1"}/>
                <input type="text" placeholder={"Add Comment Here"} className={"AddComment col-md-3"}
                    onChange={(event)=>{
                        setPostCommentValues({...postCommentValues,
                        postId: postId,text: event.target.value,userName:Auth.username});
                    }}
                />
                <button className={"AddCommentButton col-md-2"} onClick={handleReplyButtonClick}>
                    {
                        Auth.isLogin?"Add Comment"
                            : "Login/NewUser"
                    }
                </button>
            </div>
            {
                commentsData.map((data)=>
    
                    <div className={"singleComment"}  key={data.id}
                         style={darkTheme?{color:"white"}:null}
                    >
                        <div className="comment row">
                            <h6 className={"commentUsername col-4"}>@{data.userName}</h6>
                            <span className={"commentDate col-"}>Created on {"->   "}{data.createdDate}</span>
                            <span className={"comment-text col-md-10"}>{data.text}</span>
                        </div>
                        <div className="commentOptions">
                            <span className={"Reply"}>Reply</span>
                            <span onClick={()=>{
                                if (!showRepliedComment){
                                    setShowRepliedComment(true);
                                    setCommentId(data.postId);
                                }
                                else if(showRepliedComment){
                                    setShowRepliedComment(false);
                                    setCommentId(0);
                                }
                            }}>
                                {data.noOfReplies>0 ?"Show All Replies":null}
                            </span>
                        </div>
                        {
                            showRepliedComment?
                                data.repliedComments!=null?
                                    data.postId===commentId?
                                data.repliedComments.map((replyData)=>
                                    <div className={"repliedCommentSection"} key={replyData.id}>
                                        <div className="comment row" key={replyData.id}>
                                            <h6 className={"commentUsername col-4"}>@{replyData.userName}</h6>
                                            <span className={"commentDate col-"}>Created on {"->   "}
                                                {replyData.createdDate}</span>
                                            <span className={"commentReplied col-"}>{"Replied to->   "}@
                                                {replyData.repliedTo}</span>
                                            <span className={"comment-text col-md-10"}>{replyData.text}</span>
                                        </div>
                                        <span className={"commentOptionsBtn"}
                                              onClick={()=> {
                                                  postCommentReplyValues.userName = Auth.username;
                                                  postCommentReplyValues.commentId = replyData.commentId;
                                                  postCommentReplyValues.text = "Check";
                                                  postCommentReplyValues.repliedTo = replyData.userName;
                                                  postCommentReplyValues.noOfReplies=data.noOfReplies+1;

                                                  handleCommentReply();
                                              }}>Reply</span>
                                    </div>
                                )
                                        :null
                                :null
                                :null

                        }
                    </div>
                )
            }

        </div>
    );
};

export default MyComponent;
