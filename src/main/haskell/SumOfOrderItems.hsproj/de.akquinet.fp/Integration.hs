module Integration(doBenchmark) where 

import Criterion.Main

integrate :: Double -> Double -> Int -> (Double -> Double) -> Double
integrate start end precision function = sum allRectangles 
  where
    step = (end - start) / (fromIntegral precision)
    xCoordinates = map (\i -> start + (fromIntegral i) * step)
                     [ 0 .. (precision-1)]
    allRectangles = map (\x -> x * step) xCoordinates
 

testfunction _ = 1.0

integrateBench precision = integrate (-1.0) 1.9 precision testfunction

doBenchmark = defaultMain [
  bgroup "Iteration"
          [ bench "10^4"  $ whnf integrateBench (10^4)
          , bench "10^5"  $ whnf integrateBench (10^5)
          , bench "10^6"  $ whnf integrateBench (10^6)
          , bench "10^7"  $ whnf integrateBench (10^7)
          , bench "10^8"  $ whnf integrateBench (10^8)
          ]
  ]
