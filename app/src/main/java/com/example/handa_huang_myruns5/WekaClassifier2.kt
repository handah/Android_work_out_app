package com.example.handa_huang_myruns5

internal object WekaClassifier2 {
    @Throws(Exception::class)
    fun classify(i: Array<Any?>): Double {
        var p = Double.NaN
        p = N6c3b073c10(i)
        return p
    }

    fun N6c3b073c10(i: Array<Any?>): Double {
        var p = Double.NaN
        if (i[64] == null) {
            p = 0.0
        } else if ((i[64] as Double?)!!.toDouble() <= 0.362433) {
            p = 0.0
        } else if ((i[64] as Double?)!!.toDouble() > 0.362433) {
            p = N149e336811(i)
        }
        return p
    }

    fun N149e336811(i: Array<Any?>): Double {
        var p = Double.NaN
        if (i[0] == null) {
            p = 1.0
        } else if ((i[0] as Double?)!!.toDouble() <= 549.18757) {
            p = N70a97d312(i)
        } else if ((i[0] as Double?)!!.toDouble() > 549.18757) {
            p = 2.0
        }
        return p
    }

    fun N70a97d312(i: Array<Any?>): Double {
        var p = Double.NaN
        if (i[1] == null) {
            p = 3.0
        } else if ((i[1] as Double?)!!.toDouble() <= 54.709041) {
            p = N2fc1f5b013(i)
        } else if ((i[1] as Double?)!!.toDouble() > 54.709041) {
            p = 1.0
        }
        return p
    }

    fun N2fc1f5b013(i: Array<Any?>): Double {
        var p = Double.NaN
        if (i[6] == null) {
            p = 1.0
        } else if ((i[6] as Double?)!!.toDouble() <= 1.827376) {
            p = 1.0
        } else if ((i[6] as Double?)!!.toDouble() > 1.827376) {
            p = 3.0
        }
        return p
    }


//    @Throws(Exception::class)
//    fun classify(i: Array<Any?>): Double {
//        var p = Double.NaN
//        p = N1e22a43a0(i)
//        return p
//    }
//
//    fun N1e22a43a0(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 0.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 63.9443) {
//            p = N5d99f4421(i)
//        } else if ((i[0] as Double?)!!.toDouble() > 63.9443) {
//            p = N614b23776(i)
//        }
//        return p
//    }
//
//    fun N5d99f4421(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[13] == null) {
//            p = 0.0
//        } else if ((i[13] as Double?)!!.toDouble() <= 0.667299) {
//            p = 0.0
//        } else if ((i[13] as Double?)!!.toDouble() > 0.667299) {
//            p = N2e08bbfe2(i)
//        }
//        return p
//    }
//
//    fun N2e08bbfe2(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[21] == null) {
//            p = 0.0
//        } else if ((i[21] as Double?)!!.toDouble() <= 0.89504) {
//            p = N13d617153(i)
//        } else if ((i[21] as Double?)!!.toDouble() > 0.89504) {
//            p = Na77ae8c5(i)
//        }
//        return p
//    }
//
//    fun N13d617153(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[11] == null) {
//            p = 0.0
//        } else if ((i[11] as Double?)!!.toDouble() <= 0.789386) {
//            p = N185e62214(i)
//        } else if ((i[11] as Double?)!!.toDouble() > 0.789386) {
//            p = 0.0
//        }
//        return p
//    }
//
//    fun N185e62214(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[8] == null) {
//            p = 0.0
//        } else if ((i[8] as Double?)!!.toDouble() <= 1.171862) {
//            p = 0.0
//        } else if ((i[8] as Double?)!!.toDouble() > 1.171862) {
//            p = 2.0
//        }
//        return p
//    }
//
//    fun Na77ae8c5(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[2] == null) {
//            p = 0.0
//        } else if ((i[2] as Double?)!!.toDouble() <= 5.886587) {
//            p = 0.0
//        } else if ((i[2] as Double?)!!.toDouble() > 5.886587) {
//            p = 2.0
//        }
//        return p
//    }
//
//    fun N614b23776(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 1.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 557.53006) {
//            p = N4286fef47(i)
//        } else if ((i[0] as Double?)!!.toDouble() > 557.53006) {
//            p = N67c8d9526(i)
//        }
//        return p
//    }
//
//    fun N4286fef47(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 107.813836) {
//            p = N29a2c3ef8(i)
//        } else if ((i[0] as Double?)!!.toDouble() > 107.813836) {
//            p = N1697b6dc15(i)
//        }
//        return p
//    }
//
//    fun N29a2c3ef8(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[2] == null) {
//            p = 3.0
//        } else if ((i[2] as Double?)!!.toDouble() <= 12.716392) {
//            p = N1e961b999(i)
//        } else if ((i[2] as Double?)!!.toDouble() > 12.716392) {
//            p = N6c78f4e912(i)
//        }
//        return p
//    }
//
//    fun N1e961b999(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[27] == null) {
//            p = 0.0
//        } else if ((i[27] as Double?)!!.toDouble() <= 0.148604) {
//            p = N26fec3a710(i)
//        } else if ((i[27] as Double?)!!.toDouble() > 0.148604) {
//            p = N537af8d211(i)
//        }
//        return p
//    }
//
//    fun N26fec3a710(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[9] == null) {
//            p = 3.0
//        } else if ((i[9] as Double?)!!.toDouble() <= 0.675624) {
//            p = 3.0
//        } else if ((i[9] as Double?)!!.toDouble() > 0.675624) {
//            p = 0.0
//        }
//        return p
//    }
//
//    fun N537af8d211(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 87.394675) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() > 87.394675) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N6c78f4e912(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[24] == null) {
//            p = 0.0
//        } else if ((i[24] as Double?)!!.toDouble() <= 0.225329) {
//            p = 0.0
//        } else if ((i[24] as Double?)!!.toDouble() > 0.225329) {
//            p = N421c702c13(i)
//        }
//        return p
//    }
//
//    fun N421c702c13(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 89.245015) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() > 89.245015) {
//            p = N12af703314(i)
//        }
//        return p
//    }
//
//    fun N12af703314(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 0.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 93.603908) {
//            p = 0.0
//        } else if ((i[0] as Double?)!!.toDouble() > 93.603908) {
//            p = 2.0
//        }
//        return p
//    }
//
//    fun N1697b6dc15(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[1] == null) {
//            p = 1.0
//        } else if ((i[1] as Double?)!!.toDouble() <= 128.019883) {
//            p = N3d63d75b16(i)
//        } else if ((i[1] as Double?)!!.toDouble() > 128.019883) {
//            p = N2a52187b22(i)
//        }
//        return p
//    }
//
//    fun N3d63d75b16(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[64] == null) {
//            p = 1.0
//        } else if ((i[64] as Double?)!!.toDouble() <= 6.523917) {
//            p = N1cdf5dcb17(i)
//        } else if ((i[64] as Double?)!!.toDouble() > 6.523917) {
//            p = 1.0
//        }
//        return p
//    }
//
//    fun N1cdf5dcb17(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[13] == null) {
//            p = 1.0
//        } else if ((i[13] as Double?)!!.toDouble() <= 4.149797) {
//            p = N7518988b18(i)
//        } else if ((i[13] as Double?)!!.toDouble() > 4.149797) {
//            p = 1.0
//        }
//        return p
//    }
//
//    fun N7518988b18(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[32] == null) {
//            p = 1.0
//        } else if ((i[32] as Double?)!!.toDouble() <= 2.213556) {
//            p = N5e92f1a219(i)
//        } else if ((i[32] as Double?)!!.toDouble() > 2.213556) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N5e92f1a219(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[7] == null) {
//            p = 1.0
//        } else if ((i[7] as Double?)!!.toDouble() <= 12.824467) {
//            p = N7d682c2420(i)
//        } else if ((i[7] as Double?)!!.toDouble() > 12.824467) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N7d682c2420(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[9] == null) {
//            p = 3.0
//        } else if ((i[9] as Double?)!!.toDouble() <= 0.539334) {
//            p = N1bbf174721(i)
//        } else if ((i[9] as Double?)!!.toDouble() > 0.539334) {
//            p = 1.0
//        }
//        return p
//    }
//
//    fun N1bbf174721(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[3] == null) {
//            p = 1.0
//        } else if ((i[3] as Double?)!!.toDouble() <= 9.19027) {
//            p = 1.0
//        } else if ((i[3] as Double?)!!.toDouble() > 9.19027) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N2a52187b22(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[2] == null) {
//            p = 2.0
//        } else if ((i[2] as Double?)!!.toDouble() <= 52.184008) {
//            p = 2.0
//        } else if ((i[2] as Double?)!!.toDouble() > 52.184008) {
//            p = N6384f37223(i)
//        }
//        return p
//    }
//
//    fun N6384f37223(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[1] == null) {
//            p = 2.0
//        } else if ((i[1] as Double?)!!.toDouble() <= 133.570171) {
//            p = 2.0
//        } else if ((i[1] as Double?)!!.toDouble() > 133.570171) {
//            p = N5fb1fa2a24(i)
//        }
//        return p
//    }
//
//    fun N5fb1fa2a24(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[30] == null) {
//            p = 1.0
//        } else if ((i[30] as Double?)!!.toDouble() <= 4.687843) {
//            p = 1.0
//        } else if ((i[30] as Double?)!!.toDouble() > 4.687843) {
//            p = N3cd7dbb425(i)
//        }
//        return p
//    }
//
//    fun N3cd7dbb425(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[3] == null) {
//            p = 2.0
//        } else if ((i[3] as Double?)!!.toDouble() <= 100.978128) {
//            p = 2.0
//        } else if ((i[3] as Double?)!!.toDouble() > 100.978128) {
//            p = 1.0
//        }
//        return p
//    }
//
//    fun N67c8d9526(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 1423.674322) {
//            p = N4227f14b27(i)
//        } else if ((i[0] as Double?)!!.toDouble() > 1423.674322) {
//            p = N101f501229(i)
//        }
//        return p
//    }
//
//    fun N4227f14b27(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 1280.129675) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() > 1280.129675) {
//            p = N1297338f28(i)
//        }
//        return p
//    }
//
//    fun N1297338f28(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[3] == null) {
//            p = 3.0
//        } else if ((i[3] as Double?)!!.toDouble() <= 27.741537) {
//            p = 3.0
//        } else if ((i[3] as Double?)!!.toDouble() > 27.741537) {
//            p = 2.0
//        }
//        return p
//    }
//
//    fun N101f501229(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[2] == null) {
//            p = 3.0
//        } else if ((i[2] as Double?)!!.toDouble() <= 244.703725) {
//            p = N72ed67a130(i)
//        } else if ((i[2] as Double?)!!.toDouble() > 244.703725) {
//            p = N624f9b7441(i)
//        }
//        return p
//    }
//
//    fun N72ed67a130(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 1625.048088) {
//            p = Nf3d7d1e31(i)
//        } else if ((i[0] as Double?)!!.toDouble() > 1625.048088) {
//            p = N5721d16f34(i)
//        }
//        return p
//    }
//
//    fun Nf3d7d1e31(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[2] == null) {
//            p = 3.0
//        } else if ((i[2] as Double?)!!.toDouble() <= 96.74666) {
//            p = N241d2c3d32(i)
//        } else if ((i[2] as Double?)!!.toDouble() > 96.74666) {
//            p = 2.0
//        }
//        return p
//    }
//
//    fun N241d2c3d32(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[6] == null) {
//            p = 3.0
//        } else if ((i[6] as Double?)!!.toDouble() <= 22.147397) {
//            p = 3.0
//        } else if ((i[6] as Double?)!!.toDouble() > 22.147397) {
//            p = N4e68676c33(i)
//        }
//        return p
//    }
//
//    fun N4e68676c33(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 1537.861997) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() > 1537.861997) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N5721d16f34(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 3.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 2018.921555) {
//            p = N3390931835(i)
//        } else if ((i[0] as Double?)!!.toDouble() > 2018.921555) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N3390931835(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[64] == null) {
//            p = 3.0
//        } else if ((i[64] as Double?)!!.toDouble() <= 37.118966) {
//            p = 3.0
//        } else if ((i[64] as Double?)!!.toDouble() > 37.118966) {
//            p = N7ea13d9336(i)
//        }
//        return p
//    }
//
//    fun N7ea13d9336(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[19] == null) {
//            p = 3.0
//        } else if ((i[19] as Double?)!!.toDouble() <= 7.780283) {
//            p = N73ff762c37(i)
//        } else if ((i[19] as Double?)!!.toDouble() > 7.780283) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N73ff762c37(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[3] == null) {
//            p = 3.0
//        } else if ((i[3] as Double?)!!.toDouble() <= 115.599069) {
//            p = N2a8c6b9538(i)
//        } else if ((i[3] as Double?)!!.toDouble() > 115.599069) {
//            p = 2.0
//        }
//        return p
//    }
//
//    fun N2a8c6b9538(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[3] == null) {
//            p = 3.0
//        } else if ((i[3] as Double?)!!.toDouble() <= 55.440637) {
//            p = N1db3358f39(i)
//        } else if ((i[3] as Double?)!!.toDouble() > 55.440637) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N1db3358f39(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[11] == null) {
//            p = 3.0
//        } else if ((i[11] as Double?)!!.toDouble() <= 4.120351) {
//            p = 3.0
//        } else if ((i[11] as Double?)!!.toDouble() > 4.120351) {
//            p = N6a09de0f40(i)
//        }
//        return p
//    }
//
//    fun N6a09de0f40(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[10] == null) {
//            p = 2.0
//        } else if ((i[10] as Double?)!!.toDouble() <= 8.955132) {
//            p = 2.0
//        } else if ((i[10] as Double?)!!.toDouble() > 8.955132) {
//            p = 3.0
//        }
//        return p
//    }
//
//    fun N624f9b7441(i: Array<Any?>): Double {
//        var p = Double.NaN
//        if (i[0] == null) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() <= 2023.212407) {
//            p = 2.0
//        } else if ((i[0] as Double?)!!.toDouble() > 2023.212407) {
//            p = 3.0
//        }
//        return p
//    }

}