/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maps;

/**
 *
 * @author Marco De Lucchi <marcodelucchi27@gmail.com>
 */
public class NotFoundException extends Exception{
    private String address;
    
    public NotFoundException(String address){
        this.address = address;
    }
    
    public String toString(){
        return "Can't find this address: " + address;
    }
    
    public String getAddress(){
        return address;
    }
}
