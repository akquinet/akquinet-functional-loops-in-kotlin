module PasswordGeneration(test) where

import Data.Char
import Data.Vector

import Criterion.Main


plusOneChar :: Char -> Char
plusOneChar c =  chr ((ord c) + 1)

data Elem = Digit Char | Letter Char

newtype Password = Vector Elem

plusOneElem (Digit c) =
  if (c == '9') then ( Digit '0', True) else ( Digit $plusOneChar c , False)

plusOneElem (Letter c) =
  if (c == 'z') then ( Letter 'a', True) else  ( Digit $plusOneChar c, False)

-- plusOnePassword (first : remainder )

test = "hallo"
