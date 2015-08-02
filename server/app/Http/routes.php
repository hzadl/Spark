<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

//Route::get('rest', function()
//{
//   //return 'rest route is working!';
//    
//   $app = app();
//   $controller = $app->make('RestController');
//   return $controller->callAction('index', $parameters = array());
//});

//Route::controller('rest', 'RestController');


Route::get('rest', 'RestController@index');

Route::get('app', 'AppController@index');

Route::get('order', 'OrderController@index');

//Route::get('/rest/{data}', 'RestController@index');