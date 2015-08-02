<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use App\Http\Controllers\Controller;

use App\sparkUser;
use App\sparkProduct;
use App\sparkOrder;
use App\sparkStore;


class RestController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index(Request $request)
    {
        //
        
        $method = $request->input('method');
            
        switch($method)
        {
        	case 'queryOrder':
        		return $this->queryOrder($request);
        	break;
        	case 'payOrder':
        		return $this->payOrder($request);
        	break;
        	case 'createOrder':
        		return $this->createOrder($request);
        	break;
        	case 'queryProduct':
        		return $this->queryProduct($request);
        	break;
        	case 'deliverOrder':
        		return $this->deliverOrder($request);
        	break;
        	default:
        		return json_encode(array("state"=>'failed'));
        	break;
        	
        }
//        $sparkUser = sparkUser::all();
//        $u = [];
//       	foreach ($sparkUser as $user)
//     		{
//     				array_push($u, $user);
//     		}
        //var_dump($sparkUser);
        
        //return json_encode($u);
        return "";
        return json_encode(array('state'=>true));
    }
    
    private function queryProduct(Request $request)
    {
    		$sparkProducts = sparkProduct::all();
    		$res = [];
    		foreach ($sparkProducts as $sp)
    		{
    				array_push($res, $sp);
    		}
    		return json_encode($res);
    		
    }
    
    private function queryOrder(Request $request)
    {
    	$orderNumber = $request->input('order_number');
    	$sparkOrder = sparkOrder::where('order_number', $orderNumber)->first();

    	if (!empty($sparkOrder))
    	{
    		$tmp = json_decode( json_encode($sparkOrder) );
    		
    		$productIds = $tmp->product_id;
    		$tarr = explode(",", $productIds);
    		$parr = [];
    		foreach ($tarr as $pid)
    		{
    			$product = sparkProduct::where('id',$pid)->first();
    			array_push($parr, $product);
    		}
    		$tmp->products = $parr;
				return json_encode($tmp);
    	}
    	else
    	{
    		return json_encode(array("state"=>'failed'));
    	}
    	
    	
    }
    
    private function payOrder(Request $request)
    {
    	$orderNumber = $request->input('order_number');
    	$affectedRows = sparkOrder::where(array('order_number' => $orderNumber, 'state' => 1 ))->update( array('state' => 2, 'payment_time'=> time() ) );
    	
    	if ($affectedRows > 0)
    	{
    		return json_encode(array("state"=>'successed'));
    	}
    	else
    	{
    		return json_encode(array("state"=>'failed'));
    	}
    	
    }
    
    private function deliverOrder(Request $request)
    {
    	$orderNumber = $request->input('order_number');
    	$affectedRows = sparkOrder::where(array('order_number' => $orderNumber, 'state' => 2 ))->update( array('state' => 3, 'deliver_time'=> time() ) );
    	
    	if ($affectedRows > 0)
    	{
    		return json_encode(array("state"=>'successed'));
    	}
    	else
    	{
    		return json_encode(array("state"=>'failed'));
    	}
    	
    }
    
    private function createOrder(Request $request)
    {
    		$sparkOrder = new sparkOrder;
//    	$sparkOrder->order_number =  rand(pow(10, 4-1), pow(10, 4)-1);
//    	$sparkOrder->state = 1;
//    	$sparkOrder->order_time = time();
//    	$sparkOrder->price = $request->input('price');
//    	$sparkOrder->store_id = $request->input('store_id');
//    	$sparkOrder->user_id = $request->input('user_id');
    	
    	$order_number = rand(pow(10, 8-1), pow(10, 8)-1);
    	$arr = array(
    		
    		'order_number'=> $order_number,
    		'state' => 1,
    		'order_time' => time(),
    		'payment_time' => 0,
    		'deliver_time' => 0,
    		'price' => $request->input('price'),
    		'store_id' => $request->input('store_id'),
    		'user_id'=> $request->input('user_id'),
    		'product_id' => $request->input('product_id')
    		
    	);
    	
    	$res = $sparkOrder::create($arr);
    	return json_encode(array('state'=>'successed', 'order_number'=> $order_number ));
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
