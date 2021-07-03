<?php
include "conn.php";
// Check connection

 $rec_patient = $_REQUEST['biz_patient'];
$rec_chills = $_REQUEST['biz_chills'];
$rec_throat = $_REQUEST['biz_throat'];
$rec_cough = $_REQUEST['biz_cough'];
$rec_temperature = $_REQUEST['biz_temperature'];
$rec_headache = $_REQUEST['biz_headache'];
$rec_breathing = $_REQUEST['biz_breathing'];
$rec_fatigue = $_REQUEST['biz_fatigue'];
$rec_nose = $_REQUEST['biz_nose'];
$rec_diarrhea = $_REQUEST['biz_diarrhea'];
$rec_chestpain = $_REQUEST['biz_chestpain'];
$rec_patient_id = $_REQUEST['biz_patient_id'];


/* $rec_patient = "Hillary";
$rec_chills = "yes";
$rec_throat = "NO";
$rec_cough = "yes";
$rec_temperature = 36.2;
$rec_headache = "no";
$rec_breathing = "yes";
$rec_fatigue = "no";
$rec_nose = "yes";
$rec_diarrhea = "yes";
$rec_chestpain = "yes"; */

$sql = "INSERT INTO check_up (patient_names, patient_temperature, cough, chills, new_chest_pain, headache, difficulty_breathing, fatigue, runny_nose, diarrhea, sore_throat, patient_fk)
VALUES ('$rec_patient','$rec_temperature','$rec_cough','$rec_chills','$rec_chestpain','$rec_headache','$rec_breathing','$rec_fatigue','$rec_nose',
'$rec_diarrhea','$rec_throat','$rec_patient_id')";

if ($conn->query($sql) === TRUE) {
    echo "1";
} else {
    echo "0";
}

$conn->close();

?>