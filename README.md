
# Pre-work - **Simple Todo List**

**Simple Todo List** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Hengstar Gong**

Time spent: **13** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality!
* [x] Different colors for priority text and due date
* [x] Sort the list by due date
* [x] Allow users to signup/login and edit their own list
* [ ] Identify numbers or URLs items and allow the user to visit the URL directly or call the phone number
* [ ] Allow user to remove all items at once
* To be continued...

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/bE07hWc.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** The visual design feature on Android Studio is very convenient, pretty much like web UI design and far more better than the video game UI design tool I used before. The underlying concept behind Android development can be complicated and requires some time to take in.
  It uses Java language, but limited in new features in latest Java language for backward compatibility reasons and depends on the minimal API version. Debugging Android program can be painful and since even with a small change (even using Instant Run), compiling can take long when the app is big.   

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** The adapter is like a bridge that connects data and UI views. It helps keep consistent in between the data and view. It is the very basic of Android development and it is used broadly. Also, it helps list view recyling and largely reduce the memory usage when the data to display is big, as [Using an ArrayAdapter with ListView](https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView) explains.
By reading the [blog](http://android.amberfog.com/?p=296) I can tell one of the purpose of `convertView` in the `getView` is to help reuse/recycle the old view. In this way, it also helps improve the performance of the adapter as data gets bigger.

## Notes

Describe any challenges encountered while building the app.

* One challenge I encountered was when I forgot to call `notifyDataSetChanged` after removing the item. Then I tested it on emulator. Of cause it didn't work and it crashed after atempting of removing more items. I stuck a bit until I found the log says `The content of the adapter has changed but ListView did not receive a notification`.
* Persisting the data with `SQLiteOpenHelper` is definitely a challenge. I intentionally directly used `SQLiteOpenHelper` and tried to understand how it works. I spent some time to learn how to use it.
I involved into a lot of issues after introducing it. First, it crashed when I opened the app. I realized I didn't increase the DB version after I modified the table field.
Then I was not able to delete the data from DB because I used index of the item instead of auto-increamtal ID.
After using the ID field, I realized I also needed a model to store the list item and made it Serializable for intent to carry.
I also encountered crashes caused by some silly SQL syntax errors.
* Using DialogFragment is a bit intricate. Spent some time on reading the document.
* Stuck when I didn't find a way to get auto-incremental id from DBFlow apis.
Then I found the id will be filled after saving.

## License

    Copyright [2017] [Hengstar Gong]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
