# Selene minimal test example for TC56 device

This project is a minimal setup to test the hardware button capabilities of the
Zebra TC56 device. When the hardware buttons are configured to send the selene
emergency broadcast intent it should work in the following device states:

1.  device is running with screen on, app is running in foreground ✔
2.  device is running with screen on, app is running in background ✔
3.  device is running with screen locked, app was running in foreground ❌
4.  device is running with screen locked, app was running in background ❌
5.  device is running with screen off, app was running in foreground ❌
6.  device is running with screen off, app was running in background ❌

this project is setup with android studio 3.1.2 dependencies and builds are
managed with gradle.
