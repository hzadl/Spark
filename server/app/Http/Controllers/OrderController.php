<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;

class OrderController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index(Request $request)
    {
    	$pname = $request->input('pname');
    	$pid = $request->input('pid');
    	$price = $request->input('price');
    	
    	$url = "http://10.201.38.138/public/rest?method=createOrder&price=$price&store_id=1&user_id=1&product_id=$pid";
			//echo $url;     
      $j = json_decode( file_get_contents($url) );
      
				//return view('KFC_Order',);
			return view('KFC_Order')->with( array(
			'order' => $j->order_number, 
			'pname' => $pname.' $'. $price. '',
			'orderNumber' => $j->order_number
			
			
			));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  Request  $request
     * @param  int  $id
     * @return Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function destroy($id)
    {
        //
    }
}
