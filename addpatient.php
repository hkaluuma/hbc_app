<?php
include "conn.php";
// Check connection

//capture data from front end of the app
$received_disease = $_REQUEST['biz_disease'];
$received_age = $_REQUEST['biz_age'];
$received_fullname = $_REQUEST['biz_fullname'];
$received_email = $_REQUEST['biz_email'];
$received_phonenumber = $_REQUEST['biz_phonenumber'];
$received_location = $_REQUEST['biz_location'];
$received_status = $_REQUEST['biz_status'];
$received_comments = $_REQUEST['biz_comments'];

/*
$received_disease = "covid 19";
$received_age = "20";
$received_fullname = "hilla";
$received_email = "hilla@gmail.com";
$received_phonenumber = "07865432234"; 
$received_location = "kabale"; 
$received_status = "positive"; */

$sql = "INSERT INTO patient (p_fullnames, p_email, p_phonenumber, p_location, p_age, p_disease, p_test_status, p_comments)
VALUES ('$received_fullname','$received_email','$received_phonenumber','$received_location','$received_age','$received_disease','$received_status','$received_comments')";

if ($conn->query($sql) === TRUE) {
    echo "1";
} else {
    echo "0";
}

$conn->close();

?>