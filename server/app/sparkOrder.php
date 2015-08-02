<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class sparkOrder extends Model
{
    public $id = null;
    public $order_number = null;
    public $state = null;
    public $store_id = null;
    public $order_time = null;
    public $payment_time = null;
    public $deliver_time = null;
    public $product_id = null;
    public $price = null;
    public $user_id = null;    
    
    public $timestamps = false;
    
    
    protected $fillable = ['order_number', 'state', 'store_id', 'order_time', 'payment_time', 'deliver_time',
    'product_id', 'price', 'user_id'];


}
