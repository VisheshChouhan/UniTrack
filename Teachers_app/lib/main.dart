import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:teachers_app/pages/auth_page.dart';
import 'package:teachers_app/pages/login_page.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:teachers_app/pages/splash_screen.dart';
import 'firebase_options.dart';


void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );


  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      home:  SplashScreen(),
    );
  }
}
