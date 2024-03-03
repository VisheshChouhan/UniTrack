import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:csv/csv.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:path/path.dart';
import 'dart:developer';

import 'package:path_provider/path_provider.dart';
import 'package:teachers_app/pages/upload_marks.dart';

import 'UploadAssignmentScreen.dart';
import 'attendance_page.dart';
import 'chat_page2.dart';

import 'package:to_csv/to_csv.dart' as exportCSV;

class CoursePage extends StatefulWidget {
  final String CourseName;
  final String CourseCode;
  const CoursePage(
      {super.key, required this.CourseName, required this.CourseCode});

  @override
  State<CoursePage> createState() => _CoursePageState();
}

class _CoursePageState extends State<CoursePage> {


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const SizedBox(
              height: 10,
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 0),
              child: Text(
                widget.CourseName,
                style: GoogleFonts.abel(
                  textStyle: const TextStyle(
                      fontSize: 30, fontWeight: FontWeight.bold),
                ),
              ),
            ),
            const SizedBox(
              height: 0,
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10),
              child: Text(
                widget.CourseCode,
                style: GoogleFonts.abel(
                  textStyle: const TextStyle(
                      fontSize: 20, fontWeight: FontWeight.bold),
                ),
              ),
            ),
            const SizedBox(
              height: 50,
            ),
            TakeAttendance(
              courseCode: widget.CourseCode,
            ),
            SizedBox(
              height: 10,
            ),
            CourseChatTile(widget.CourseCode),
            const SizedBox(
              height: 10,
            ),
            DownloadAttendance(courseCode: widget.CourseCode),
            const SizedBox(
              height: 10,
            ),
            UploadMarks(
              courseCode: widget.CourseCode,
            ),
            const SizedBox(
              height: 10,
            ),
            UploadAssignments(courseCode: widget.CourseCode)

          ],
        ),
      ),
    );
  }
}



class UploadAssignments extends StatelessWidget {
  final String courseCode;
  const UploadAssignments({
    super.key,
    required this.courseCode,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: GestureDetector(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => UploadAssignmentsScreen(
                  courseCode: courseCode,
                )),
          );
        },
        child: Container(
          padding: EdgeInsets.all(12),
          height: 75,
          width: 500,
          decoration: BoxDecoration(
              color: Colors.pink[100], borderRadius: BorderRadius.circular(12)),
          child: Center(
            child: Row(
              children: [
                const Image(
                  image: AssetImage("lib/assets/subject_images/cloud_database.png"),
                  height: 50,
                  width: 50,
                ),
                SizedBox(
                  width: 10,
                ),
                Text('Upload Assignments',
                    style: GoogleFonts.abel(
                      textStyle: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    )),
              ],
            ),
          ),
        ),
      ),
    );
  }
}




class TakeAttendance extends StatelessWidget {
  final String courseCode;
  const TakeAttendance({
    super.key,
    required this.courseCode,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: GestureDetector(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => AttendanceScreen(
                      courseCode: courseCode,
                    )),
          );
        },
        child: Container(
          padding: EdgeInsets.all(12),
          height: 75,
          width: 500,
          decoration: BoxDecoration(
              color: Colors.pink[100], borderRadius: BorderRadius.circular(12)),
          child: Center(
            child: Row(
              children: [
                const Image(
                  image: AssetImage("lib/assets/icons/immigration.png"),
                  height: 50,
                  width: 50,
                ),
                SizedBox(
                  width: 10,
                ),
                Text('Take Attendance for the ongoing class',
                    style: GoogleFonts.abel(
                      textStyle: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    )),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class DownloadAttendance extends StatelessWidget {
  final String courseCode;
  const DownloadAttendance({
    super.key,
    required this.courseCode,
  });

  Future<List<Map<String, Set<String>>>> fetchAttendanceData() async {
    QuerySnapshot<Map<String, dynamic>> querySnapshot = await FirebaseFirestore
        .instance
        .collection('Courses')
        .doc(courseCode)
        .collection("Attendance")
        .get();

    print("avc");
    log("query snapshot${querySnapshot.size}");
    List<Map<String, dynamic>> attendanceData = [];
    List<Map<String, Set<String>>> finalData = [];

    Set<String> temp = {};

    var abc = await FirebaseFirestore.instance
        .collection('Courses')
        .doc(courseCode)
        .collection("Attendance")
        .get()
        .then((dateDetails) async {
      for (var dateDocument in dateDetails.docs) {
        temp = await readRow(dateDocument.id);
        print("TEMP $temp");
        finalData.add({dateDocument.id: temp});
        print("Adding to final DATA ${{dateDocument.id: temp}}");
        print("After adding $finalData");
      }
      ;
    });
    print("FINAL DATA $finalData");
    return finalData;

    // You may need to return a value or handle the data outside the Future.wait
    //return finalData;

    //return finalData;
  }

  Future<Set<String>> readRow(String date) async {
    Set<String> result = {};
    await FirebaseFirestore.instance
        .collection('Courses')
        .doc(courseCode)
        .collection("Attendance")
        .doc(date)
        .collection('present')
        .get()
        .then((studentDetails) {
      for (var student in studentDetails.docs) {
        //adding student name to 2d list
        String tempString = student["name"];
        print("tempString $tempString");
        result.add(tempString);
      }
    });

    return result;
  }

  Future<List<Map<String, Set<String>>>> tempFunction2() async {
    var val = await fetchAttendanceData();
    print("reslut2 ${val}");
    return val;
  }

  Future<void> exportCSV1() async {
    /*List<String> header = [];
    header.add('No.');
    header.add('User Name');
    header.add('Mobile');
    header.add('ID Number');

    List<List<String>> listOfLists = [];
    List<String> data1 = ['1', 'Bilal Saeed', '1374934', '912839812'];
    List<String> data2 = ['2', 'Ahmar', '21341234', '192834821'];

    listOfLists.add(data1);
    listOfLists.add(data2);

    exportCSV.myCSV(header, listOfLists);*/
    List<Map<String, Set<String>>> attendanceData = await fetchAttendanceData();

    Set<String> studentName = {};
    List<String> attendanceDates = [];

    for (Map<String, Set<String>> dateAttendace in attendanceData) {
      dateAttendace.forEach((key, value) {
        attendanceDates.add(key);
        for (var element in value) {
          studentName.add(element);
        }
      });
    }


    int rows = studentName.length;
    int cols = attendanceDates.length;

    List<List<String>> attendanceList = List.generate(rows, (index) => List<String>.filled(cols, "Absent", growable: true));

    print("AttendanceList initital $attendanceList");

    List<String> stuName = studentName.toList();
    print("stuName length${stuName.length}");
    print("StuName $stuName");
    //attendanceList.insert(0, stuName);
    int temp = 0;

        for (List<String> row in attendanceList){
          row.insert(0, stuName[temp]);
          row.add("0");
          row.add(cols.toString());
          row.add("0");
          temp+=1;
        }

        


    print("attendanceList after inserting stuName $attendanceList");


    int colNumber= 0;

    for (Map<String, Set<String>> dateAttendace in attendanceData) {
      colNumber += 1;
      dateAttendace.forEach((key, value) {

        for (var element in value) {
          print("element : $element :index: ${stuName.indexOf(element,0)} :stuName: $stuName");

          int rowNumber = stuName.indexOf(element,0);

          print("rowNumber $rowNumber :  colNumber $colNumber");


          attendanceList[rowNumber][colNumber] = "Present";
          int currAttendance = int.parse(attendanceList[rowNumber][cols+1]);
          attendanceList[rowNumber][cols+1] = (currAttendance+1).toString();

          attendanceList[rowNumber][cols+3] = "${((currAttendance+1)/cols)*100}%";

          print("Attendance List $attendanceList");
        }
      });
      print("Attendance List $attendanceList");
    }

    attendanceDates.insert(0, "Student Names");
    attendanceDates.add("Attended");
    attendanceDates.add("Total");
    attendanceDates.add("In Percentage");
    exportCSV.myCSV(attendanceDates, attendanceList);



  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: GestureDetector(
        onTap: () {
          //exportAttendanceToCsv();
          exportCSV1();
          //fetchAttendanceData();
          //tempFunction();
          ScaffoldMessenger.of(context)
              .showSnackBar(const SnackBar(content: Text("file created")));
          /*Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) =>  AttendanceScreen(courseCode: courseCode,)),
          );*/
        },
        child: Container(
          padding: EdgeInsets.all(12),
          height: 75,
          width: 500,
          decoration: BoxDecoration(
              color: Colors.pink[100], borderRadius: BorderRadius.circular(12)),
          child: Center(
            child: Row(
              children: [
                const Image(
                  image:
                      AssetImage("lib/assets/icons/attendance_uncoloured.png"),
                  height: 50,
                  width: 50,
                ),
                SizedBox(
                  width: 10,
                ),
                Text('Download Attendance till date',
                    style: GoogleFonts.abel(
                      textStyle: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    )),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class CourseChatTile extends StatelessWidget {
  final String Code;
  const CourseChatTile(String this.Code, {super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: GestureDetector(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => ChatPage2(Code)),
          );
        },
        child: Container(
          padding: const EdgeInsets.all(12),
          height: 75,
          width: 500,
          decoration: BoxDecoration(
              color: Colors.deepPurple[100],
              borderRadius: BorderRadius.circular(12)),
          child: Center(
            child: Row(
              children: [
                const Image(
                  image: AssetImage("lib/assets/icons/comments.png"),
                  height: 40,
                  width: 50,
                ),
                SizedBox(
                  width: 10,
                ),
                Text('Group Page',
                    style: GoogleFonts.abel(
                      textStyle: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    )),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class UploadMarks extends StatelessWidget {
  final String courseCode;
  const UploadMarks({
    super.key,
    required this.courseCode,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: GestureDetector(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => bulkUpload(
                      courseCode: courseCode,
                    )),
          );
        },
        child: Container(
          padding: EdgeInsets.all(12),
          height: 75,
          width: 500,
          decoration: BoxDecoration(
              color: Colors.deepPurple[100],
              borderRadius: BorderRadius.circular(12)),
          child: Center(
            child: Row(
              children: [
                const Image(
                  image: AssetImage("lib/assets/icons/marketing.png"),
                  height: 45,
                  width: 50,
                ),
                SizedBox(
                  width: 10,
                ),
                Text('Upload Marks',
                    style: GoogleFonts.abel(
                      textStyle: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    )),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
