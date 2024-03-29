// File generated by FlutterFire CLI.
// ignore_for_file: lines_longer_than_80_chars, avoid_classes_with_only_static_members
import 'package:firebase_core/firebase_core.dart' show FirebaseOptions;
import 'package:flutter/foundation.dart'
    show defaultTargetPlatform, kIsWeb, TargetPlatform;

/// Default [FirebaseOptions] for use with your Firebase apps.
///
/// Example:
/// ```dart
/// import 'firebase_options.dart';
/// // ...
/// await Firebase.initializeApp(
///   options: DefaultFirebaseOptions.currentPlatform,
/// );
/// ```
class DefaultFirebaseOptions {
  static FirebaseOptions get currentPlatform {
    if (kIsWeb) {
      return web;
    }
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return android;
      case TargetPlatform.iOS:
        return ios;
      case TargetPlatform.macOS:
        return macos;
      case TargetPlatform.windows:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for windows - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      case TargetPlatform.linux:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for linux - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      default:
        throw UnsupportedError(
          'DefaultFirebaseOptions are not supported for this platform.',
        );
    }
  }

  static const FirebaseOptions web = FirebaseOptions(
    apiKey: 'AIzaSyDloLjpsiHzQFJZPwcCTRHzb3evfWakLRw',
    appId: '1:391876471580:web:37929c327c6c842ad5e8d9',
    messagingSenderId: '391876471580',
    projectId: 'login-5d101',
    authDomain: 'login-5d101.firebaseapp.com',
    databaseURL: 'https://login-5d101-default-rtdb.firebaseio.com',
    storageBucket: 'login-5d101.appspot.com',
  );

  static const FirebaseOptions android = FirebaseOptions(
    apiKey: 'AIzaSyAkOXTmity-vf3ujOXHhR2KoahyXra9-RI',
    appId: '1:391876471580:android:e891d158827e2596d5e8d9',
    messagingSenderId: '391876471580',
    projectId: 'login-5d101',
    databaseURL: 'https://login-5d101-default-rtdb.firebaseio.com',
    storageBucket: 'login-5d101.appspot.com',
  );

  static const FirebaseOptions ios = FirebaseOptions(
    apiKey: 'AIzaSyDDOajH-L13YsHSFbfmreWZvpR-0Hq9ctY',
    appId: '1:391876471580:ios:0b75b2df73a2d7d8d5e8d9',
    messagingSenderId: '391876471580',
    projectId: 'login-5d101',
    databaseURL: 'https://login-5d101-default-rtdb.firebaseio.com',
    storageBucket: 'login-5d101.appspot.com',
    iosClientId: '391876471580-7vl7h08apki9t75mi0m8mmh2qlsqsrp4.apps.googleusercontent.com',
    iosBundleId: 'com.example.teachersApp',
  );

  static const FirebaseOptions macos = FirebaseOptions(
    apiKey: 'AIzaSyDDOajH-L13YsHSFbfmreWZvpR-0Hq9ctY',
    appId: '1:391876471580:ios:0b75b2df73a2d7d8d5e8d9',
    messagingSenderId: '391876471580',
    projectId: 'login-5d101',
    databaseURL: 'https://login-5d101-default-rtdb.firebaseio.com',
    storageBucket: 'login-5d101.appspot.com',
    iosClientId: '391876471580-7vl7h08apki9t75mi0m8mmh2qlsqsrp4.apps.googleusercontent.com',
    iosBundleId: 'com.example.teachersApp',
  );
}
