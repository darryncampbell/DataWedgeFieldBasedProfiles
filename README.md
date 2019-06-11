# DataWedge Field-Based Profiles

### This application is provided without guarantee or waranty

Sample app to show how to mimic field based profile switching with DataWedge on Android:

![Screenshot for Profile 1](https://github.com/darryncampbell/DataWedgeFieldBasedProfiles/blob/master/media/profile1.jpg?raw=true)

![Screenshot for Profile 3](https://github.com/darryncampbell/DataWedgeFieldBasedProfiles/blob/master/media/profile3.jpg?raw=true)

It is possible today to associate a DataWedge Profile with an Android application and an activity within that application.  The Windows Mobile / Windows CE implementation of DataWedge allowed association with an application and a ‘Window title’ so it was possible to specify a separate profile for any Window (capital ‘W’) within that app including, text boxes, text areas etc.

This has led to several Android developers requesting the ability to automatically switch profiles based on the selected text field, like how DataWedge used to work on Windows Mobile / CE.  This is ‘on the backlog’ & may be added in the future but developers are already able to achieve this today using the DataWedge Intent API.

With the following APIs an Android application can switch between profiles dynamically depending on which text field has focus:
* [SWITCH_TO_PROFILE](http://techdocs.zebra.com/datawedge/latest/guide/api/switchtoprofile/): Instructs DataWedge to switch to a specific profile.  This can be invoked when the desired text field receives focus
* [GET_ACTIVE_PROFILE](http://techdocs.zebra.com/datawedge/latest/guide/api/getactiveprofile/): Retrieves the current active profile.  This can optionally be used to confirm the profile has been applied successfully.
