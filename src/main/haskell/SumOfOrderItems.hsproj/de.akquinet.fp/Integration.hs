module Integration(doBenchmark) where 

import Criterion.Main

integrate :: Double -> Double -> Int -> (Double -> Double) -> Double
integrate start end precision function = sum allRectangles 
  where
    step = (end - start) / (fromIntegral precision)
    xCoordinates = map (\i -> start + (fromIntegral i) * step)
                     [ 0 .. (precision-1)]
    allRectangles = map (\x -> (function x) * step) xCoordinates

-- is surprisingly slower
integrate2 start end precision function = loop (0.0, start)
 where
  step = (end - start) / (fromIntegral precision)

  loop (accu, x)
    | x > end = accu
    | otherwise = loop ((accu + (function x) * step),
                        (x+step))


testfunction _ = 1.0

integrateBench precision = integrate (-1.0) 1.0 precision testfunction
integrateBench2 precision = integrate2 (-1.0) 1.0 precision testfunction

doBenchmark = defaultMain [
  bgroup "Integration on container"
          [ bench "10^4"  $ whnf integrateBench (10^4)
          , bench "10^5"  $ whnf integrateBench (10^5)
          , bench "10^6"  $ whnf integrateBench (10^6)
          , bench "10^7"  $ whnf integrateBench (10^7)
          , bench "10^8"  $ whnf integrateBench (10^8)
          ]
 ,
  bgroup "Integration on loop"
              [ bench "10^4"  $ whnf integrateBench2 (10^4)
              , bench "10^5"  $ whnf integrateBench2 (10^5)
              , bench "10^6"  $ whnf integrateBench2 (10^6)
              , bench "10^7"  $ whnf integrateBench2 (10^7)
              , bench "10^8"  $ whnf integrateBench2 (10^8)
              ]
  ]
