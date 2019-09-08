module SumOfOrderItems(runTestSuite) where

import Test.HUnit

data OrderItem =
  OrderItem  { orderNumber :: String
             , amount :: Int
             } deriving (Show)
                
sumOrderItems :: [OrderItem] -> Int

sumOrderItems items = summedAmount where
  amounts = map amount items
  summedAmount = sum amounts                

sumOrderItems2 items = sum (map amount items)


testOrderItemList = [ OrderItem "123" 3, OrderItem "456" 2] 

typicalUsageTest = TestCase $ assertEqual "sum 2 Items" 5
   ( sumOrderItems testOrderItemList)

wrongResultTest = TestCase $ assertBool "sum 2 Items with wrong outcome"  
  ( 1 /= ( sumOrderItems testOrderItemList ) )

allTests = TestList 
  [ typicalUsageTest
  , wrongResultTest
  ]

runTestSuite  = runTestTT allTests
