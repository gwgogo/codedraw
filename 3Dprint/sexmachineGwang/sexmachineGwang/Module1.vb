Imports System.Drawing

Module Module1
    Structure Triangle
        'Dim intXN As Single
        'Dim intYN As Single
        'Dim intZN As Single
        Dim intX1 As Single
        Dim intY1 As Single
        Dim intZ1 As Single
        Dim intX2 As Single
        Dim intY2 As Single
        Dim intZ2 As Single
        Dim intX3 As Single
        Dim intY3 As Single
        Dim intZ3 As Single
        'Dim intABC As UInt16
    End Structure

    Dim image, destImage As Bitmap
    Dim bitUpdateingSize As Boolean = False
    Dim bitCancelExport As Boolean = False
    Dim bitUpdateNeeded As Boolean = True
    Dim bitWorking As Boolean = False
    Dim bitBinMode As Boolean = True
    Dim intErrorCount As UInt16 = 0
    Const conEmptySingle As Single = 0
    Const conEmptyUInt16 As UInt16 = 0
    Dim txtX As String, txtY As String
    Dim sngImageXData(,) As Single
    Dim sngImageYData(,) As Single
    Dim sngImageZData(,) As Single
    Dim intMinZ As UInt32

    Public Sub Main(ByVal sArgs() As String)

        If sArgs.Length = 1 Then
            LoadImage(sArgs(0))
        Else
            Console.WriteLine("아규먼트 형식을 확인해주세요.")
        End If

    End Sub

    Sub LoadImage(ByRef filePath As String)
        image = New Bitmap(filePath)
        image.Save("result.bmp")

        Dim X As Integer = image.Width
        Dim Y As Integer = image.Height
        bitUpdateingSize = True

        If X > Y Then
            txtX = 100
            txtY = Y / X * 100
        Else
            txtY = 100
            txtX = X / Y * 100
        End If
        bitUpdateingSize = False
        bitUpdateNeeded = True
        convertImage()

    End Sub

    Public Function ThumbnailCallback() As Boolean
        Return False
    End Function

    Private Sub convertImage()
        If bitWorking Then
        ElseIf bitUpdateNeeded Then
            Try
                Dim myCallback As New Image.GetThumbnailImageAbort(AddressOf ThumbnailCallback)

                bitUpdateNeeded = False
                bitWorking = True

                Console.WriteLine("기본 이미지 데이터를 빌딩하고 있습니다...")

                Dim X As Integer
                Dim Y As Integer

                'Dim dblImageZ As Double = Val(txtZ.Text)
                'Dim dblBaseZ As Double = Val(txtBase.Text)

                'Dim intBaseZC As UInt16 = 255 - 255 / (dblImageZ + dblBaseZ) * dblBaseZ
                'Dim dblImageZRatio As Double = (intBaseZC) / 255

                Dim intImageWidth As Integer = image.Size.Width
                Dim intImageHeight As Integer = image.Size.Height
                Dim bitDoBW As Boolean = False
                Dim bitDoAlpha As Boolean = True
                Dim bitInvert As Boolean = False

                Dim sngTargetWidth As Single = Val(txtX)
                Dim sngTargetHeight As Single = Val(txtY)

                'Dim objPic As System.Drawing.Image
                'Dim objSource As System.Drawing.Bitmap = picSource.Image

                Dim objTarget As New System.Drawing.Bitmap(image)

                'We don't want to scale UP a side if we don't need to, the math will correct it later.
                Dim sngRes = Val("0.25")
                If sngTargetWidth / sngRes < intImageWidth And sngTargetHeight / sngRes < intImageHeight Then
                    intImageWidth = sngTargetWidth / sngRes
                    intImageHeight = sngTargetHeight / sngRes
                    objTarget = objTarget.GetThumbnailImage(intImageWidth, intImageHeight, myCallback, IntPtr.Zero)
                ElseIf sngTargetWidth / sngRes < intImageWidth Then
                    intImageWidth = sngTargetWidth / sngRes
                    objTarget = objTarget.GetThumbnailImage(intImageWidth, intImageHeight, myCallback, IntPtr.Zero)
                ElseIf sngTargetHeight / sngRes < intImageHeight Then
                    intImageHeight = sngTargetHeight / sngRes
                    objTarget = objTarget.GetThumbnailImage(intImageWidth, intImageHeight, myCallback, IntPtr.Zero)
                End If

                Dim objColor As System.Drawing.Color
                Dim intNewGray As UInt16
                Dim intThresh As Integer = 0.75

                Dim objBaseColor As System.Drawing.Color
                Dim objTopColor As System.Drawing.Color

                If bitInvert Then
                    objBaseColor = Color.White
                    objTopColor = Color.Black
                Else
                    objBaseColor = Color.Black
                    objTopColor = Color.White
                End If

                destImage = objTarget

                For X = 1 To intImageWidth
                    For Y = 1 To intImageHeight
                        objColor = objTarget.GetPixel(X - 1, Y - 1)

                        'Compute gray
                        intNewGray = (Int(objColor.R) + Int(objColor.B) + Int(objColor.G)) / 3

                        'Compute Alpha
                        If bitDoAlpha Then
                            intNewGray = 255 - ((255 - intNewGray) * objColor.A / 255)
                        End If

                        If bitDoBW Then
                            If intNewGray >= intThresh Then
                                objTarget.SetPixel(X - 1, Y - 1, objTopColor)
                            Else
                                objTarget.SetPixel(X - 1, Y - 1, objBaseColor)
                            End If
                        Else
                            If bitInvert Then
                                objTarget.SetPixel(X - 1, Y - 1, Color.FromArgb(255 - intNewGray, 255 - intNewGray, 255 - intNewGray))
                            Else
                                objTarget.SetPixel(X - 1, Y - 1, Color.FromArgb(intNewGray, intNewGray, intNewGray))
                            End If
                        End If
                    Next
                    If bitUpdateNeeded Then
                        bitWorking = False
                        Exit Sub
                    End If
                Next



                Dim intLoops As UInt16 = 0
                Dim intSpikesFound As UInt32 = 0
                Dim intMaxLoops As Int16 = 0

                intMaxLoops += 10

                intMaxLoops += 10

                Console.WriteLine("스파이크를 검출하고 있습니다...")

                If True Then
                    Dim bitSpikeFound As Boolean

                    Dim intMinFriends As UInt16 = 4

                    Do
                        destImage = objTarget

                        bitSpikeFound = False

                        For X = 1 To intImageWidth - 2
                            For Y = 1 To intImageHeight - 2

                                If CheckForSpikeAt(X, Y, objTarget, intMinFriends, intSpikesFound) Then
                                    bitSpikeFound = True
                                    If X > 1 And X < intImageWidth - 2 Then
                                        If Y > 1 And Y < intImageHeight - 2 Then
                                            CheckForSpikeAt(X - 1, Y - 1, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X, Y - 1, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X + 1, Y - 1, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X - 1, Y, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X + 1, Y, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X - 1, Y + 1, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X, Y + 1, objTarget, intMinFriends, intSpikesFound)
                                            CheckForSpikeAt(X + 1, Y + 1, objTarget, intMinFriends, intSpikesFound)
                                        End If
                                    End If
                                End If
                            Next

                            If bitUpdateNeeded Then
                                bitWorking = False
                                Exit Sub
                            End If

                        Next
                        intLoops += 1
                    Loop While bitSpikeFound And intLoops < intMaxLoops
                End If

                If True Then
                    Dim bitSpikeFound As Boolean

                    Dim intMinFriends As UInt16 = 4


                    Do
                        destImage = objTarget

                        bitSpikeFound = False

                        For X = 1 To intImageWidth - 2
                            For Y = 1 To intImageHeight - 2

                                If CheckForSpikeAt(X, Y, objTarget, intMinFriends, intSpikesFound, True) Then
                                    bitSpikeFound = True
                                    If X > 1 And X < intImageWidth - 2 Then
                                        If Y > 1 And Y < intImageHeight - 2 Then
                                            CheckForSpikeAt(X - 1, Y - 1, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X, Y - 1, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X + 1, Y - 1, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X - 1, Y, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X + 1, Y, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X - 1, Y + 1, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X, Y + 1, objTarget, intMinFriends, intSpikesFound, True)
                                            CheckForSpikeAt(X + 1, Y + 1, objTarget, intMinFriends, intSpikesFound, True)
                                        End If
                                    End If
                                End If
                            Next

                            If bitUpdateNeeded Then
                                bitWorking = False
                                Exit Sub
                            End If

                        Next
                        intLoops += 1
                    Loop While bitSpikeFound And intLoops < intMaxLoops
                End If

                destImage = objTarget

                BuildVectorData()

                Console.WriteLine("이미지 변환이 완료되었습니다!")
            Catch ex As Exception
                intErrorCount += 1
            End Try

            intErrorCount = 0

            bitWorking = False

        End If
    End Sub

    Function CheckForSpikeAt(ByRef X As UInt16, ByRef Y As UInt16, ByRef objTarget As Bitmap, ByRef intMinFriends As UInt16, ByRef intSpikesFound As UInt32, Optional ByRef bitAntiMode As Boolean = False) As Boolean
        Dim objTallFriend As UInt16 = 255
        Dim objTargetPixel As UInt16 = objTarget.GetPixel(X, Y).R
        Dim intFriends As UInt16 = 0
        Dim bitCouldBeSpike As Boolean = True

        If bitAntiMode Then objTallFriend = 0

        bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X - 1, Y - 1).R, objTallFriend, intFriends, bitAntiMode)
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X, Y - 1).R, objTallFriend, intFriends, bitAntiMode)
        End If
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X + 1, Y - 1).R, objTallFriend, intFriends, bitAntiMode)
        End If
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X - 1, Y).R, objTallFriend, intFriends, bitAntiMode)
        End If
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X + 1, Y).R, objTallFriend, intFriends, bitAntiMode)
        End If
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X - 1, Y + 1).R, objTallFriend, intFriends, bitAntiMode)
        End If
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X, Y + 1).R, objTallFriend, intFriends, bitAntiMode)
        End If
        If bitCouldBeSpike Then
            bitCouldBeSpike = FriendLogic(objTargetPixel, objTarget.GetPixel(X + 1, Y + 1).R, objTallFriend, intFriends, bitAntiMode)
        End If

        If bitCouldBeSpike Then
            If intFriends < intMinFriends Then
                objTarget.SetPixel(X, Y, Color.FromArgb(objTallFriend, objTallFriend, objTallFriend))
                intSpikesFound += 1
                Return True
                'End iF
            Else
                Return False
            End If
        Else
            Return False
        End If
    End Function

    Function FriendLogic(ByRef objTarget As UInt16, ByRef objFreind As UInt16, ByRef objTallFriend As UInt16, ByRef intFreindCount As UInt16, ByRef bitAntiMode As Boolean) As Boolean
        If bitAntiMode Then
            'Returns true if a freind isn't shorter.
            If objTarget < objFreind Then
                Return False
            Else
                If objFreind > objTallFriend And objFreind < objTarget Then
                    objTallFriend = objFreind
                ElseIf objFreind = objTarget Then
                    intFreindCount += 1
                End If
                Return True
            End If
        Else
            'Returns true if a freind isn't taller.
            If objTarget > objFreind Then
                Return False
            Else
                If objFreind < objTallFriend And objFreind > objTarget Then
                    objTallFriend = objFreind
                ElseIf objFreind = objTarget Then
                    intFreindCount += 1
                End If
                Return True
            End If
        End If
    End Function

    Sub BuildVectorData()

        Console.WriteLine("벡터 데이터를 빌딩하고 있습니다...")


        Dim intImageWidth As Integer = destImage.Width
        Dim intImageHeight As Integer = destImage.Height
        ReDim sngImageXData(intImageWidth + 1, intImageHeight + 1)
        ReDim sngImageYData(intImageWidth + 1, intImageHeight + 1)
        ReDim sngImageZData(intImageWidth + 1, intImageHeight + 1)

        Dim dblImageBase As Double = Val("1")

        Dim dblScaleX As Double = CDbl(txtX) / CDbl(intImageWidth)
        Dim dblScaleY As Double = CDbl(txtY) / CDbl(intImageHeight)
        Dim dblScaleZ As Double = CDbl("10") / 255

        'Dim intHeights(objImage.Size.Width + 2, objImage.Size.Height + 2) As Single
        Dim intTotalHeight As UInt16 = Val(10) + Val(1)

        intMinZ = 10000

        Dim objImage As Bitmap = destImage
        Dim bitDoBase As Boolean = True
        Dim sngValue As Single

        For X = 0 To intImageWidth - 1
            For Y = 0 To intImageHeight - 1
                sngValue = dblImageBase + (255 - CDbl(objImage.GetPixel(X, Y).R)) * dblScaleZ
                sngImageZData(X + 1, (intImageHeight) - Y) = sngValue
                If sngValue < intMinZ Then
                    intMinZ = sngValue
                End If
            Next
        Next

        CreateSTLFile("test.stl")
    End Sub

    Sub CreateSTLFile(ByRef strFile As String)
        Console.WriteLine("STL 파일을 생성하고 있습니다...")

        Dim bitInverted As Boolean = False

        Dim strHeader As String

        Dim X As UInt32
        Dim Y As UInt32


        'Dim objTriangles(intImageWidth * intImageHeight * 12) As Triangle
        Dim objTriangle As Triangle
        Dim objTriangleC As UInt32 = 0


        Dim objFile As IO.FileStream

        'Try
        strHeader = Space(80)
        Mid(strHeader, 1, 8) = "STL File - Created With: Image to STL Converter (http://goo.gl/wWFMx)"


        Try
            FileSystem.Kill(strFile)
        Catch ex As Exception

        End Try

        objFile = IO.File.Open(strFile, IO.FileMode.Create, IO.FileAccess.Write, IO.FileShare.None)

        'Write header.
        If bitBinMode Then
            WriteByteArray(strHeader, objFile)
            WriteByteArray(objTriangleC, objFile)
        Else
            WriteByteArray("solid object" & vbNewLine, objFile)
        End If



        Dim objImage As System.Drawing.Bitmap = destImage
        Dim intImageWidth As Integer = objImage.Size.Width
        Dim intImageHeight As Integer = objImage.Size.Height
        Dim dblImageBase As Double = Val("1")

        Dim dblScaleX As Double = CDbl(txtX) / CDbl(intImageWidth)
        Dim dblScaleY As Double = CDbl(txtY) / CDbl(intImageHeight)
        Dim dblScaleZ As Double = CDbl("10") / 255


        Dim intHeights(objImage.Size.Width + 2, objImage.Size.Height + 2) As Single
        Dim intTotalHeight As UInt16 = Val(10) + Val(1)

        Dim bitTopDone(objImage.Size.Width + 2, objImage.Size.Height + 2) As Boolean
        Dim bitBottomDone(2, 2) As Boolean

        Dim intMinDepth As UInt32 = 255
        Dim bitDoBase As Boolean = True

        For X = 0 To intImageWidth - 1
            For Y = 0 To intImageHeight - 1
                If bitInverted Then
                    intHeights(X + 1, (intImageHeight) - Y) = dblImageBase + CDbl(objImage.GetPixel(X, Y).R) * dblScaleZ
                Else
                    intHeights(X + 1, (intImageHeight) - Y) = dblImageBase + (255 - CDbl(objImage.GetPixel(X, Y).R)) * dblScaleZ
                End If
                If intHeights(X + 1, (intImageHeight) - Y) < intMinDepth Then
                    intMinDepth = intHeights(X + 1, (intImageHeight) - Y)
                End If
            Next
        Next

        'Point order is important to calculate an "outside surface"!

        If intMinDepth > 0 Then
            bitDoBase = False

            'Lets do a simple base...
            objTriangleC += 1
            With objTriangle
                .intX1 = (intImageWidth + 1) * dblScaleX
                .intY1 = 0
                .intZ1 = 0

                .intX2 = 0
                .intY2 = 0
                .intZ2 = 0

                .intX3 = 0
                .intY3 = (intImageHeight + 1) * dblScaleY
                .intZ3 = 0
            End With
            WriteTriangle(objTriangle, objFile)

            objTriangleC += 1
            With objTriangle
                .intX1 = (intImageWidth + 1) * dblScaleX
                .intY1 = 0
                .intZ1 = 0

                .intX2 = 0
                .intY2 = (intImageHeight + 1) * dblScaleY
                .intZ2 = 0

                .intX3 = (intImageWidth + 1) * dblScaleX
                .intY3 = (intImageHeight + 1) * dblScaleY
                .intZ3 = 0
            End With
            WriteTriangle(objTriangle, objFile)
        Else
            'Waste not want not!
            ReDim bitBottomDone(objImage.Size.Width + 2, objImage.Size.Height + 2)
        End If

        Dim I As UInt16
        Dim intTemp1 As UInt16
        Dim intTemp2 As UInt16
        'Dim intHeight As Single
        Dim intMatchSize As UInt16
        Dim intBlockSize As UInt16
        Dim bitNoMatch As Boolean

        'I should NOT need to do this!
        For X = 0 To intImageWidth
            For Y = 0 To intImageHeight
                bitTopDone(X, Y) = False
            Next
        Next

        For X = 0 To intImageWidth
            For Y = 0 To intImageHeight
                If Not bitTopDone(X, Y) Then
                    'If not bitDoBase, we know we have to map everything, skip the check.
                    If (Not bitDoBase) OrElse
                        intHeights(X, Y) > 0 OrElse
                        intHeights(X + 1, Y) > 0 OrElse
                        intHeights(X, Y + 1) > 0 OrElse
                        intHeights(X + 1, Y + 1) > 0 Then

                        intBlockSize = 0
                        bitNoMatch = False

                        intTemp1 = intImageWidth - X
                        intTemp2 = intImageHeight - Y
                        If intTemp1 > intTemp2 Then
                            intMatchSize = intTemp1
                        Else
                            intMatchSize = intTemp2
                        End If


                        For I = 1 To intMatchSize
                            For intTemp1 = 0 To I
                                'Yes I know we do one ex6tra check per loop.
                                If bitTopDone(X, Y) Then
                                    bitNoMatch = True
                                    Exit For
                                Else
                                    If intHeights(X, Y) <> intHeights(X + I, Y + intTemp1) Then
                                        bitNoMatch = True
                                        Exit For
                                    ElseIf intHeights(X, Y) <> intHeights(X + intTemp1, Y + I) Then
                                        bitNoMatch = True
                                        Exit For
                                    End If
                                End If
                            Next
                            If bitNoMatch Then
                                Exit For
                            Else
                                intBlockSize = I - 1
                            End If
                        Next

                        If intBlockSize < 0 Then
                            intBlockSize = 0
                        End If

                        For intTemp1 = 0 To intBlockSize
                            For intTemp2 = 0 To intBlockSize
                                bitTopDone(X + intTemp1, Y + intTemp2) = True
                            Next
                        Next

                        intBlockSize += 1

                        'Below
                        objTriangleC += 1
                        With objTriangle
                            .intX1 = X * dblScaleX
                            .intY1 = Y * dblScaleY
                            .intZ1 = intHeights(X, Y)

                            .intX2 = (X + intBlockSize) * dblScaleX
                            .intY2 = (Y + intBlockSize) * dblScaleY
                            .intZ2 = intHeights(X + intBlockSize, Y + intBlockSize)

                            .intX3 = (X) * dblScaleX
                            .intY3 = (Y + intBlockSize) * dblScaleY
                            .intZ3 = intHeights(X, Y + intBlockSize)
                        End With
                        WriteTriangle(objTriangle, objFile)

                        'Right
                        objTriangleC += 1
                        With objTriangle
                            .intX1 = X * dblScaleX
                            .intY1 = Y * dblScaleY
                            .intZ1 = intHeights(X, Y)

                            .intX2 = (X + intBlockSize) * dblScaleX
                            .intY2 = Y * dblScaleY
                            .intZ2 = intHeights(X + intBlockSize, Y)

                            .intX3 = (X + intBlockSize) * dblScaleX
                            .intY3 = (Y + intBlockSize) * dblScaleY
                            .intZ3 = intHeights(X + intBlockSize, Y + intBlockSize)
                        End With
                        WriteTriangle(objTriangle, objFile)

                    End If
                End If
            Next
            If bitDoBase Then
            Else
            End If
            If bitCancelExport Then
                Exit For
            End If
        Next

        If bitDoBase And Not bitCancelExport Then
            For X = 0 To objImage.Size.Width
                For Y = 0 To objImage.Size.Height
                    If Not bitBottomDone(X, Y) Then
                        'If not bitDoBase, we know we have to map everything, skip the check.
                        If intHeights(X, Y) > 0 OrElse
                            intHeights(X, Y) > 0 OrElse
                            intHeights(X + 1, Y) > 0 OrElse
                            intHeights(X, Y + 1) > 0 OrElse
                            intHeights(X + 1, Y + 1) > 0 Then





                            intBlockSize = 0
                            bitNoMatch = False

                            intTemp1 = intImageWidth - X
                            intTemp2 = intImageHeight - Y
                            If intTemp1 > intTemp2 Then
                                intMatchSize = intTemp1
                            Else
                                intMatchSize = intTemp2
                            End If


                            For I = 1 To intMatchSize
                                For intTemp1 = 0 To I
                                    'Yes I know we do one extra check per loop.
                                    If bitBottomDone(X, Y) Then
                                        bitNoMatch = True
                                        Exit For
                                    Else
                                        If intHeights(X + I, Y + intTemp1) = 0 Then
                                            bitNoMatch = True
                                            Exit For
                                        ElseIf intHeights(X + intTemp1, Y + I) = 0 Then
                                            bitNoMatch = True
                                            Exit For
                                        End If
                                    End If
                                Next
                                If bitNoMatch Then
                                    Exit For
                                Else
                                    intBlockSize = I - 1
                                End If
                            Next

                            If intBlockSize < 0 Then
                                intBlockSize = 0
                            End If

                            For intTemp1 = 0 To intBlockSize
                                For intTemp2 = 0 To intBlockSize
                                    bitBottomDone(X + intTemp1, Y + intTemp2) = True
                                Next
                            Next

                            intBlockSize += 1

                            'Below
                            objTriangleC += 1
                            With objTriangle
                                .intX1 = X * dblScaleX
                                .intY1 = Y * dblScaleY
                                .intZ1 = 0

                                .intX2 = X * dblScaleX
                                .intY2 = (Y + intBlockSize) * dblScaleY
                                .intZ2 = 0

                                .intX3 = (X + intBlockSize) * dblScaleX
                                .intY3 = (Y + intBlockSize) * dblScaleY
                                .intZ3 = 0
                            End With
                            WriteTriangle(objTriangle, objFile)

                            'Right
                            objTriangleC += 1
                            With objTriangle
                                .intX1 = X * dblScaleX
                                .intY1 = Y * dblScaleY
                                .intZ1 = 0

                                .intX2 = (X + intBlockSize) * dblScaleX
                                .intY2 = (Y + intBlockSize) * dblScaleY
                                .intZ2 = 0

                                .intX3 = (X + intBlockSize) * dblScaleX
                                .intY3 = Y * dblScaleY
                                .intZ3 = 0
                            End With
                            WriteTriangle(objTriangle, objFile)
                        End If
                    End If
                Next
                If bitCancelExport Then
                    Exit For
                End If
            Next
        End If


        If bitBinMode Then
            WriteTriangleCount(objTriangleC, objFile)
        Else
            WriteByteArray("endsolid" & vbNewLine, objFile)
        End If

        Try
            objFile.Close()
        Catch ex As Exception

        End Try
        Console.WriteLine("STL 파일 생성이 완료되었습니다!")
    End Sub

    Sub WriteTriangleCount(ByRef intTriangleC As UInt32, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(intTriangleC)
        objFile.Seek(80, IO.SeekOrigin.Begin)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As Single, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As Double, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As Int16, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As Int32, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As UInt16, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As UInt32, ByRef objFile As IO.FileStream)
        Dim bytBytes As Byte() = BitConverter.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteByteArray(ByRef objVar As String, ByRef objFile As IO.FileStream)
        Dim utfTemp As New System.Text.UTF8Encoding()
        Dim bytBytes As Byte() = utfTemp.GetBytes(objVar)
        For Each bytByte In bytBytes
            objFile.WriteByte(bytByte)
        Next
    End Sub

    Sub WriteTriangle(ByRef objTriangle As Triangle, ByRef objFile As IO.FileStream)
        'Binary STL File format:
        '***************************************
        'UINT8[80] – Header
        'UINT32 – Number of triangles
        '
        'foreach triangle
        'REAL32[3] – Normal vector
        'REAL32[3] – Vertex 1
        'REAL32[3] – Vertex 2
        'REAL32[3] – Vertex 3
        'UINT16 – Attribute byte count
        'End
        '***************************************

        With objTriangle
            '.intXN = (.intY2 - .intY1) * (.intZ3 - .intZ1) - (.intZ2 - .intZ1) * (.intY3 - .intY1)
            '.intYN = (.intZ2 - .intZ1) * (.intX3 - .intX1) - (.intY2 - .intY1) * (.intZ3 - .intZ1)
            '.intZN = (.intX2 - .intX1) * (.intY3 - .intY1) - (.intX2 - .intX1) * (.intX3 - .intX1)

            If bitBinMode Then
                'WriteByteArray(.intXN, objFile)
                'WriteByteArray(.intYN, objFile)
                'WriteByteArray(.intZN, objFile)
                WriteByteArray(conEmptySingle, objFile)
                WriteByteArray(conEmptySingle, objFile)
                WriteByteArray(conEmptySingle, objFile)


                WriteByteArray(.intX1, objFile)
                WriteByteArray(.intY1, objFile)
                WriteByteArray(.intZ1, objFile)

                WriteByteArray(.intX2, objFile)
                WriteByteArray(.intY2, objFile)
                WriteByteArray(.intZ2, objFile)

                WriteByteArray(.intX3, objFile)
                WriteByteArray(.intY3, objFile)
                WriteByteArray(.intZ3, objFile)

                'WriteByteArray(.intABC, objFile)
                WriteByteArray(conEmptyUInt16, objFile)

            Else
                WriteByteArray("outer loop" & vbNewLine, objFile)
                WriteByteArray("vertex " & .intX1 & " " & .intY1 & " " & .intZ1 & vbNewLine, objFile)
                WriteByteArray("vertex " & .intX2 & " " & .intY2 & " " & .intZ2 & vbNewLine, objFile)
                WriteByteArray("vertex " & .intX3 & " " & .intY3 & " " & .intZ3 & vbNewLine, objFile)
                WriteByteArray("endloop" & vbNewLine, objFile)
                WriteByteArray("endfacet" & vbNewLine, objFile)
            End If
        End With
    End Sub

End Module
