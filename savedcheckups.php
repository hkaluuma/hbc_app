<?php
require_once "conn.php";
$sql ="SELECT check_up_time, patient_temperature, cough, chills, new_chest_pain, headache, sore_throat, runny_nose, difficulty_breathing, fatigue FROM check_up";

//if($conn->query($sql)){
	//echo "Error in connecting to Database.";
//}else{
	$result = $conn->query($sql);
	if($result->num_rows > 0){
		$return_arr['checkup'] = array();
		   while($row = $result->fetch_array()) {
			array_push($return_arr['checkup'], array(
            'check_up_time'=>$row['check_up_time'],
            'patient_temperature'=>$row['patient_temperature'],
            'cough'=>$row['cough'],
            'chills'=>$row['chills'],
            'new_chest_pain'=>$row['new_chest_pain'],
            'headache'=>$row['headache'],
            'sore_throat'=>$row['sore_throat'],
            'runny_nose'=>$row['runny_nose'],
            'difficulty_breathing'=>$row['difficulty_breathing'],
            'fatigue'=>$row['fatigue']
			));
		}
		echo json_encode($return_arr);
	}
//}
?>