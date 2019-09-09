module PasswordGeneration where

import Data.Char
import Data.String
import Data.List as L
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
  if (c == 'z') then ( lowestLetter, True) else  ( Letter $plusOneChar c, False)

elemToChar (Digit c) = c
elemToChar (Letter c) = c

nextLowestElem (Digit _) = lowestLetter
nextLowestElem (Letter _) = lowestDigit

type Password = Vector Elem

--instance Show Password where
--  show password =

passwordToString :: Password -> String
passwordToString password = V.toList vectorOfChars  where
  elemsInReadableOrder = V.reverse password
  vectorOfChars = V.map elemToChar elemsInReadableOrder

seedPassword = V.singleton $ Letter 'a'

plusOnePassword password =
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


attackFunctional1 checkPassword = L.find checkPassword passwords where
  passwordsAsVectors = iterate plusOnePassword seedPassword
  passwords = Prelude.map passwordToString passwordsAsVectors

attackFunctional2 checkPassword = attackFunctionalIteration seedPassword where
  attackFunctionalIteration password =
    if (checkPassword passwordAsString)
      then passwordAsString
      else attackFunctionalIteration $ plusOnePassword password
    where
      passwordAsString = passwordToString password

benchAttackFunctional1 password = attackFunctional1 (\p -> p == password )
benchAttackFunctional2 password = attackFunctional2 (\p -> p == password )

doBenchmark = defaultMain [
  bgroup "Benchmarking the functional password attack with a list"
          [ bench "0a"  $ whnf benchAttackFunctional1 "0a"
          , bench "0a0a"  $ whnf benchAttackFunctional1 "0a0a"
          , bench "0a0a0a"  $ whnf benchAttackFunctional1 "0a0a0a"
          --, bench "0a0a0a0a"  $ whnf benchAttackFunctional1 "0a0a0a0a"
          ] ,
  bgroup "Benchmarking the functional password attack with a recursion"
            [ bench "0a"  $ whnf benchAttackFunctional2 "0a"
            , bench "0a0a"  $ whnf benchAttackFunctional2 "0a0a"
            , bench "0a0a0a"  $ whnf benchAttackFunctional2 "0a0a0a"
           -- , bench "0a0a0a0a"  $ whnf benchAttackFunctional2 "0a0a0a0a"
            ]
  ]
