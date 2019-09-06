module PasswordGeneration(test) where

import Data.Char
import Data.Vector as V

import Criterion.Main


plusOneChar :: Char -> Char
plusOneChar c =  chr ((ord c) + 1)

data Elem = Digit Char | Letter Char
  deriving Show

lowestDigit = Digit '0'
lowestLetter = Letter 'a'

plusOneElem (Digit c) =
  if (c == '9') then ( lowestDigit, True) else ( Digit $plusOneChar c , False)

plusOneElem (Letter c) =
  if (c == 'z') then ( lowestLetter, True) else  ( Digit $plusOneChar c, False)

nextLowestElem (Digit _) = lowestLetter
nextLowestElem (Letter _) = lowestDigit

newtype Password = Vector Elem

seedPassword = V.singleton $ Letter 'a'

plusOnePassword password
  | V.null password = seedPassword -- Check, if I need this, also in Kotlin
  | otherwise  =
      if (overflow) then  overflowPassword else nonOverflowPassword
      where
        (increasedFirstElem, overflow) = plusOneElem $ V.head password
        elementsRemainder = V.tail password
        overflowPassword = cons increasedFirstElem remainderPlusOne
          where
            remainderPlusOne = if (V.null elementsRemainder)
              then
                V.singleton $ nextLowestElem increasedFirstElem
              else
                plusOnePassword elementsRemainder
        nonOverflowPassword = cons increasedFirstElem elementsRemainder

second = plusOnePassword seedPassword

test = (show second)
